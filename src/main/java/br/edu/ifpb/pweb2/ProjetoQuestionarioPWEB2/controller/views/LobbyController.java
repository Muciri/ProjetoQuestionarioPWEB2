package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.CorridaService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.ResultadoService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.SessaoCorridaService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/lobby")
public class LobbyController {

    private final CorridaService corridaService;
    private final ResultadoService resultadoService;
    private final SessaoCorridaService sessaoCorridaService;

    public LobbyController(CorridaService corridaService, ResultadoService resultadoService, SessaoCorridaService sessaoCorridaService) {
        this.corridaService = corridaService;
        this.resultadoService = resultadoService;
        this.sessaoCorridaService = sessaoCorridaService;
    }

    @GetMapping
    public String lobby(HttpSession session, Model model) {
        Participante participante = (Participante) session.getAttribute("participante");

        //pausa o cronometro qnd sair da corrida
        sessaoCorridaService.pausar(session);

        Corrida corridaAtiva = sessaoCorridaService.corridaAtiva(session);

        model.addAttribute("corridas", corridaService.listarAtivas());
        model.addAttribute("corridasFeitasIds", resultadoService.getCorridasFeitasIds(participante));
        model.addAttribute("corridaAtivaId", corridaAtiva != null ? corridaAtiva.getId() : null);
        model.addAttribute("indicePerguntaAtual", corridaAtiva != null ? sessaoCorridaService.getIndicePergunta(session) : 0);

        return "lobby";
    }
}
