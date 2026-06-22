package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Resultado;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.CorridaService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.ParticipanteService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.ResultadoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

// UC09 — Participante consulta ranking geral
@Controller
@RequestMapping("/ranking")
public class RankingController {

    private final ResultadoService resultadoService;
    private final CorridaService corridaService;
    private final ParticipanteService participanteService;

    public RankingController(ResultadoService resultadoService, CorridaService corridaService, ParticipanteService participanteService) {
        this.resultadoService = resultadoService;
        this.corridaService = corridaService;
        this.participanteService = participanteService;
    }

    // Backlog 22 — ranking geral
    @GetMapping
    public String rankingGeral(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               Model model, Principal principal) {
        Participante participante = participanteService.findByNome(principal.getName());

        Pageable pageable = montarPaginacao(page, size);
        Page<Resultado> resultados = resultadoService.buscarRankingGeral(pageable);

        boolean participanteTemPontuacao = resultadoService.participanteTemPontuacao(participante);

        model.addAttribute("resultados", resultados);
        model.addAttribute("tamanhoPagina", pageable.getPageSize());
        model.addAttribute("participanteLogadoId", participante.getId());
        model.addAttribute("participanteTemPontuacao", participanteTemPontuacao);
        model.addAttribute("corridaFiltro", null);

        return "ranking/ranking";
    }

    // Backlog 23 — ranking por corrida
    @GetMapping("/{corridaId}")
    public String rankingPorCorrida(@PathVariable Long corridaId,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size,
                                    Model model, Principal principal) {
        Participante participante = participanteService.findByNome(principal.getName());
        var corrida = corridaService.buscarPorId(corridaId);

        Pageable pageable = montarPaginacao(page, size);
        Page<Resultado> resultados = resultadoService.buscarRankingPorCorrida(corrida, pageable);

        boolean participanteTemPontuacao = resultadoService.participanteTemPontuacao(participante, corrida);

        model.addAttribute("resultados", resultados);
        model.addAttribute("tamanhoPagina", pageable.getPageSize());
        model.addAttribute("participanteLogadoId", participante.getId());
        model.addAttribute("participanteTemPontuacao", participanteTemPontuacao);
        model.addAttribute("corridaFiltro", corrida);

        return "ranking/ranking";
    }

    // Garante valores válidos para o tamanho da página informado pelo usuário
    private Pageable montarPaginacao(int page, int size) {
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 5;
        }
        if (size > 100) {
            size = 100;
        }
        return PageRequest.of(page, size);
    }
}
