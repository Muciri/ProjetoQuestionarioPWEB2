package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception.CorridaInvalidaException;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception.RespostaInvalidaException;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Pergunta;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Resultado;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.CorridaService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.ParticipanteService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.ResultadoService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.SessaoCorridaService;
import jakarta.servlet.http.HttpSession;

/**
 *
 * Fluxo (POST-Redirect-GET, conforme slide "Spring MVC - Redirect e Flash"):
 *   1. Participante clica em "Iniciar" no lobby -> GET /corrida/{id}/iniciar
 *   2. Este controlador valida e salva a corrida na sessão (Backlog 13, 14)
 *   3. Redireciona para o fluxo de perguntas do Felipe
 *   4. Ao final (ou timeout), Felipe redireciona para GET /corrida/{id}/resultado
 *   5. Este controlador persiste o Resultado e mostra a tela final (Backlog 21)
 */
@Controller
@RequestMapping("/corrida")
public class CorridaExecucaoController {

    private static final Logger logger = LoggerFactory.getLogger(CorridaExecucaoController.class);

    private final CorridaService corridaService;
    private final SessaoCorridaService sessaoCorridaService;
    private final ResultadoService resultadoService;
    private final ParticipanteService participanteService;

    public CorridaExecucaoController(CorridaService corridaService,
                                     SessaoCorridaService sessaoCorridaService,
                                     ResultadoService resultadoService,
                                     ParticipanteService participanteService) {
        this.corridaService = corridaService;
        this.sessaoCorridaService = sessaoCorridaService;
        this.resultadoService = resultadoService;
        this.participanteService = participanteService;
    }

    // Backlog 13 + 14: valida corrida, salva timestamp na sessão, redireciona
    @GetMapping("/{id}/iniciar")
    public String iniciar(@PathVariable Long id, HttpSession session, RedirectAttributes flash, Principal principal) {

        Corrida corridaAtiva = sessaoCorridaService.corridaAtiva(session);
        if (corridaAtiva != null) {
            if (corridaAtiva.getId().equals(id)) {
                int indice = sessaoCorridaService.getIndicePergunta(session);
                return "redirect:/corrida/" + id + "/pergunta/" + indice;
            }
            flash.addFlashAttribute("aviso",
                    "Corrida já começou! Termine a corrida atual antes de iniciar outra.");
            return "redirect:/lobby";
        }

        Corrida corrida = buscarCorridaOuFalhar(id);

        // RN03: corrida sem perguntas ou inválida não pode iniciar
        if (!corridaService.corridaValida(corrida)) {
            throw new CorridaInvalidaException(
                    "A corrida \"" + corrida.getTitulo() + "\" não pode ser iniciada (sem perguntas ou tempo inválido).");
        }

        sessaoCorridaService.iniciarCorrida(session, corrida);
        logger.info("Corrida {} iniciada para participante {}",
                corrida.getId(), principal.getName());

        return "redirect:/corrida/" + id + "/pergunta/0";
    }

    @GetMapping("/{id}/pergunta/{indice}")
    public String exibirPergunta(@PathVariable Long id,
                             @PathVariable int indice,
                             HttpSession session,
                             Model model,
                             RedirectAttributes flash) {

    Corrida corrida = buscarCorridaOuFalhar(id);

    Corrida corridaAtiva = sessaoCorridaService.corridaAtiva(session);

    if (corridaAtiva == null || !corridaAtiva.getId().equals(id)) {
        flash.addFlashAttribute("erro", "Você precisa iniciar uma corrida primeiro.");
        return "redirect:/lobby";
    }

    int indiceSessao = sessaoCorridaService.getIndicePergunta(session);

    if (indice != indiceSessao){
        return "redirect:/corrida/" + id + "/pergunta/" + indiceSessao;
    }

    //religa o cronometro dps da pausa
    sessaoCorridaService.retomar(session);

    if (sessaoCorridaService.tempoRestante(session) == 0) {
        flash.addFlashAttribute("aviso", "Tempo esgotado!");
        return "redirect:/corrida/" + id + "/resultado";
    }

    List<Pergunta> perguntas = corrida.getPerguntas();

    if (perguntas == null || perguntas.isEmpty()) {
        flash.addFlashAttribute("erro", "Essa corrida não possui perguntas cadastradas.");
        return "redirect:/lobby";
    }

     if (indiceSessao < 0 || indiceSessao >= perguntas.size()) {
        return "redirect:/corrida/" + id + "/resultado";
    }


    Pergunta perguntaAtual = perguntas.get(indiceSessao);
    


    model.addAttribute("corrida", corrida);
    model.addAttribute("pergunta", perguntaAtual);
    model.addAttribute("indiceAtual", indiceSessao);
    model.addAttribute("totalPerguntas", perguntas.size());
    model.addAttribute("tempoRestante", sessaoCorridaService.tempoRestante(session));

    return "corrida/pergunta";
}

    @PostMapping("/{id}/responder")
    public String responderPergunta(@PathVariable Long id, @RequestParam("respostaSelecionada") Integer respostaSelecionada, HttpSession session, RedirectAttributes flash){
             
        Corrida corrida = buscarCorridaOuFalhar(id);
        Corrida corridaAtiva = sessaoCorridaService.corridaAtiva(session);

        if (corridaAtiva == null || !corridaAtiva.getId().equals(id)) {
            flash.addFlashAttribute("erro", "Você precisa iniciar uma corrida primeiro.");
            return "redirect:/lobby";
        }

        if (sessaoCorridaService.tempoRestante(session) == 0) {
            flash.addFlashAttribute("aviso", "Tempo esgotado!");
            return "redirect:/corrida/" + id + "/resultado";
        }

        int indiceAtual = sessaoCorridaService.getIndicePergunta(session);
        List<Pergunta> perguntas = corrida.getPerguntas();

        if (perguntas == null || perguntas.isEmpty() || indiceAtual >= perguntas.size()){
            return "redirect:/corrida/" + id + "/resultado";
        }
        
        Pergunta perguntaAtual = perguntas.get(indiceAtual);

        if (respostaSelecionada == null
                || respostaSelecionada < 0
                || respostaSelecionada >= perguntaAtual.getAlternativas().size()) {
            throw new RespostaInvalidaException();
        }

        if(respostaSelecionada.equals(perguntaAtual.getRespostaCorreta())){
            sessaoCorridaService.incrementarAcertos(session);
            sessaoCorridaService.somarPontuacao(session, perguntaAtual.getPontos());
            flash.addFlashAttribute("mensagem", "Resposta correta!");
        }else{
            flash.addFlashAttribute("erro", "Resposta incorreta!");
        }

        sessaoCorridaService.avancarPergunta(session);
        int proximoIndice = sessaoCorridaService.getIndicePergunta(session);

        if(proximoIndice >= perguntas.size()){
            flash.addFlashAttribute("mensagem", "Corrida finalizada!!");
            return "redirect:/corrida/" + id + "/resultado";
        }
        return "redirect:/corrida/" + id + "/pergunta/" + proximoIndice;
    }


    // Tela de resultado - Felipe redireciona aqui após última pergunta ou timeout
    @GetMapping("/{id}/resultado")
    public String resultado(@PathVariable Long id, HttpSession session, Model model, Principal principal) {
        Participante participante = participanteService.findByNome(principal.getName());
        Corrida corrida = buscarCorridaOuFalhar(id);

        Corrida corridaAtual = sessaoCorridaService.corridaAtiva(session);
        boolean chegadaNormal = corridaAtual != null && corridaAtual.getId().equals(id);

        if (chegadaNormal) {
            preencherResultadoNovo(model, session, participante, corrida);
        } else {
            // Refresh ou acesso direto - busca resultado anterior na base
            return preencherResultadoExistente(model, participante, corrida);
        }

        return "corrida/resultado";
    }

    // Métodos privados auxiliares (separação de responsabilidades)

    private Corrida buscarCorridaOuFalhar(Long id) {
        return corridaService.buscarPorId(id);
    }

    private void preencherResultadoNovo(Model model, HttpSession session,
                                         Participante participante, Corrida corrida) {
        int acertos = sessaoCorridaService.getAcertos(session);
        int pontuacaoFinal = sessaoCorridaService.getPontuacao(session);
        long tempoRestante = sessaoCorridaService.tempoRestante(session);
        boolean expirou = tempoRestante == 0;

        // Backlog 21 / RN02: salva resultado (retorna null se já existe)
        Resultado resultado = resultadoService.salvarResultado(participante, corrida, pontuacaoFinal);
        sessaoCorridaService.encerrarCorrida(session);

        boolean jaParticipou = (resultado == null);
        BigDecimal pontuacao = BigDecimal.valueOf(pontuacaoFinal);

        logger.info("Resultado registrado - participante: {}, corrida: {}, pontuação: {}, expirou: {}, jaParticipou: {}",
                participante.getNome(), corrida.getTitulo(), pontuacao, expirou, jaParticipou);

        model.addAttribute("corrida", corrida);
        model.addAttribute("acertos", acertos);
        model.addAttribute("totalPerguntas", corrida.getPerguntas().size());
        model.addAttribute("pontuacao", pontuacao);
        model.addAttribute("expirou", expirou);
        model.addAttribute("jaParticipou", jaParticipou);
        model.addAttribute("mensagem", expirou
                ? "Tempo esgotado! Você marcou " + pontuacao + " ponto(s)."
                : "Parabéns! Você marcou " + pontuacao + " ponto(s)!");
    }

    private String preencherResultadoExistente(Model model, Participante participante, Corrida corrida) {
        return resultadoService.buscarUltimo(participante, corrida)
                .map(r -> {
                    model.addAttribute("corrida", corrida);
                    model.addAttribute("pontuacao", r.getPontuacao());
                    model.addAttribute("acertos", null);
                    model.addAttribute("totalPerguntas", corrida.getPerguntas().size());
                    model.addAttribute("expirou", false);
                    model.addAttribute("mensagem",
                            "Resultado da sua participação anterior: " + r.getPontuacao() + " ponto(s).");
                    return "corrida/resultado";
                })
                .orElse("redirect:/lobby");
    }
}
