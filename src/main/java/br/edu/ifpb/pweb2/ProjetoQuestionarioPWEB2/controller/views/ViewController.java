package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titulo", "Projeto Questionário");
        model.addAttribute("mensagem", "Bem-vindo! O sistema está funcionando");
        return "index";
    }
}
