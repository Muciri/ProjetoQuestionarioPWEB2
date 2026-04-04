package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Pergunta;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PerguntaController {

    @Autowired
    private PerguntaRepository perguntaRepository;

    @GetMapping("/perguntas")
    public String listarPerguntas(Model model) {
        List<Pergunta> perguntas = perguntaRepository.findAll();

        model.addAttribute("titulo", "Lista de Perguntas");
        model.addAttribute("mensagem", "Perguntas cadastradas no sistema");
        model.addAttribute("perguntas", perguntas);

        return "perguntas/listaPerguntas";
    }
}