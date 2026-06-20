package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.CorridaService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.ParticipanteService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.ResultadoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String rankingGeral(HttpSession session, Model model, Principal principal) {
        // REVISAR ISSO DAQUI JAJÁ

        // Participante participante = (Participante) session.getAttribute("participante");
        Participante participante = participanteService.findByNome(principal.getName());

        var resultados = resultadoService.buscarRankingGeral();

        boolean participanteTemPontuacao = resultados.stream()
                .anyMatch(r -> r.getParticipante().getId().equals(participante.getId()));

        model.addAttribute("resultados", resultados);
        model.addAttribute("participanteLogadoId", participante.getId());
        model.addAttribute("participanteTemPontuacao", participanteTemPontuacao);
        model.addAttribute("corridaFiltro", null);

        return "ranking/ranking";
    }

    // Backlog 23 — ranking por corrida
    @GetMapping("/{corridaId}")
    public String rankingPorCorrida(@PathVariable Long corridaId, HttpSession session, Model model, Principal principal) {
        //Participante participante = (Participante) session.getAttribute("participante");
        Participante participante = participanteService.findByNome(principal.getName());
        var corrida = corridaService.buscarPorId(corridaId);
        var resultados = resultadoService.buscarRankingPorCorrida(corrida);

        boolean participanteTemPontuacao = resultados.stream()
                .anyMatch(r -> r.getParticipante().getId().equals(participante.getId()));

        model.addAttribute("resultados", resultados);
        model.addAttribute("participanteLogadoId", participante.getId());
        model.addAttribute("participanteTemPontuacao", participanteTemPontuacao);
        model.addAttribute("corridaFiltro", corrida);

        return "ranking/ranking";
    }
}
