package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Pergunta;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.PerguntaRepository;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.CorridaService;
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
    private CorridaService corridaService;

    @Autowired
    public PerguntaController(PerguntaRepository perguntaRepository, PerguntaService perguntaService) {
        this.perguntaRepository = perguntaRepository;
        this.perguntaService = perguntaService;
    }

    @GetMapping("")
    public ModelAndView listarPerguntas(
            @RequestParam(value = "corridaId", required = false) Long corridaId,
            ModelAndView model,
            RedirectAttributes redirectAttributes) {

        model.setViewName("perguntas/listaPerguntas");
        model.addObject("titulo", "Lista de Perguntas");
        model.addObject("corridas", perguntaService.getCorridas());
        model.addObject("corridaIdSelecionada", corridaId);

        if (corridaId != null) {
            model.addObject("perguntas", perguntaService.getPerguntasPorCorrida(corridaId));
        } else {
            model.addObject("perguntas", perguntaService.getPerguntas());
        }

        return model;
    }

@GetMapping("/form")
public ModelAndView exibirFormularioCadastro(
        @RequestParam(required = false) Long corridaId,
        ModelAndView model) {
    model.setViewName("perguntas/formularioPergunta");
    model.addObject("pergunta", new Pergunta());
    model.addObject("titulo", "Nova Pergunta");
    model.addObject("corridaId", corridaId);
    model.addObject("corridas", corridaService.listar()); // lista pra o select
    return model;
}
    @PostMapping("/salvar")
    public ModelAndView salvarPergunta(@ModelAttribute("pergunta") Pergunta pergunta,@RequestParam(required = false) Long corridaId,  RedirectAttributes redirectAttributes) {

        if (corridaId != null){
            Corrida corrida  = corridaService.buscarPorId(corridaId);
            pergunta.setCorrida(corrida);
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
        model.setViewName("perguntas/formularioPergunta");
        Pergunta pergunta = perguntaService.getPerguntaById(id);

        model.addObject("pergunta", pergunta);
        model.addObject("titulo", "Editar Pergunta");

        redirectAttributes.addFlashAttribute("mensagem", "pergunta salva com sucesso!");
        return model;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirPergunta(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        perguntaService.deletePerguntaById(id);

        redirectAttributes.addFlashAttribute("mensagem", "pergunta excluída com sucesso!");
        return new ModelAndView("redirect:/perguntas");
    }
}