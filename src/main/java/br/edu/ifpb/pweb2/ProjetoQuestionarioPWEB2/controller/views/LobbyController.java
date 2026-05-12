package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.CorridaService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.ResultadoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// UC07 — Participante vê sala de corridas
@Controller
@RequestMapping("/lobby")
public class LobbyController {

    private final CorridaService corridaService;
    private final ResultadoService resultadoService;

    public LobbyController(CorridaService corridaService, ResultadoService resultadoService) {
        this.corridaService = corridaService;
        this.resultadoService = resultadoService;
    }

    @GetMapping
    public String lobby(HttpSession session, Model model) {
        Participante participante = (Participante) session.getAttribute("participante");

        model.addAttribute("corridas", corridaService.listarAtivas());
        model.addAttribute("corridasFeitasIds", resultadoService.getCorridasFeitasIds(participante));

        return "lobby";
    }
}
