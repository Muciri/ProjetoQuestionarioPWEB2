package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Pergunta;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.PerguntaRepository;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.PerguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/perguntas")
public class PerguntaController {
    private final PerguntaRepository perguntaRepository;
    private final PerguntaService perguntaService;

    @Autowired
    public PerguntaController(PerguntaRepository perguntaRepository, PerguntaService perguntaService) {
        this.perguntaRepository = perguntaRepository;
        this.perguntaService = perguntaService;
    }

    // galera, coloquei esse método aqui por que ainda não tem feito o lance do admin e tal
    // depois de implementar a autenticação e interceptadores, vou refatorar este código, tirar a verificação repetida
    private boolean isUsuarioAdmin() {
        return true;
    }
    
    @GetMapping("")
    public ModelAndView listarPerguntas(ModelAndView model, RedirectAttributes redirectAttributes) {
        if (!isUsuarioAdmin()) {
            redirectAttributes.addFlashAttribute("erro", "acesso negado");
            return new ModelAndView("redirect:/perguntas");
        }

        model.setViewName("perguntas/listaPerguntas");
        model.addObject("titulo", "Lista de Perguntas");
        model.addObject("perguntas", perguntaService.getPerguntas());
        model.addObject("isAdmin", isUsuarioAdmin());

        return model;
    }

    @GetMapping("/form")
    public ModelAndView exibirFormularioCadastro(ModelAndView model, RedirectAttributes redirectAttributes) {
        if (!isUsuarioAdmin()) {
            redirectAttributes.addFlashAttribute("erro", "acesso negado");
            return new ModelAndView("redirect:/perguntas");
        }

        model.setViewName("perguntas/formularioPergunta");
        model.addObject("pergunta", new Pergunta());
        model.addObject("titulo", "Nova Pergunta");
        return model;
    }

    @PostMapping("/salvar")
    public ModelAndView salvarPergunta(@ModelAttribute("pergunta") Pergunta pergunta, RedirectAttributes redirectAttributes) {
        if (!isUsuarioAdmin()) {
            redirectAttributes.addFlashAttribute("erro", "acesso negado");
            return new ModelAndView("redirect:/perguntas");
        }

        if (pergunta.getId() != null) {
            perguntaService.UpdatePergunta(pergunta.getId(), pergunta);
        } else {
            perguntaService.createPergunta(pergunta);
        }

        redirectAttributes.addFlashAttribute("mensagem", "pergunta salva com sucesso!");
        return new ModelAndView("redirect:/perguntas");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView exibirFormularioEdicao(@PathVariable Long id,ModelAndView model, RedirectAttributes redirectAttributes) {
        if (!isUsuarioAdmin()) {
            redirectAttributes.addFlashAttribute("erro", "acesso negado");
            return new ModelAndView("redirect:/perguntas");
        }

        model.setViewName("perguntas/formularioPergunta");
        Pergunta pergunta = perguntaService.getPerguntaById(id);

        model.addObject("pergunta", pergunta);
        model.addObject("titulo", "Editar Pergunta");

        redirectAttributes.addFlashAttribute("mensagem", "pergunta salva com sucesso!");
        return model;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirPergunta(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!isUsuarioAdmin()) {
            redirectAttributes.addFlashAttribute("erro", "acesso negado");
            return new ModelAndView("redirect:/perguntas");
        }

        perguntaService.deletePerguntaById(id);

        redirectAttributes.addFlashAttribute("mensagem", "pergunta excluída com sucesso!");
        return new ModelAndView("redirect:/perguntas");
    }
}