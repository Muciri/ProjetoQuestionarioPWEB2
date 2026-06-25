package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Pergunta;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.PerguntaRepository;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.CorridaService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.PerguntaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

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
    model.addObject("corridas", corridaService.listar());
    return model;
}

    @PostMapping("/salvar")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView salvarPergunta(@Valid @ModelAttribute("pergunta") Pergunta pergunta, BindingResult bindingResult, @RequestParam(required = false) Long corridaId, @RequestParam(value="imagem", required = false) MultipartFile imagem, ModelAndView model, RedirectAttributes redirectAttributes) {

        if (corridaId != null) {
            Corrida corrida = corridaService.buscarPorId(corridaId);
            pergunta.setCorrida(corrida);
        }

        if (bindingResult.hasErrors()) {
            model.setViewName("perguntas/formularioPergunta");
            model.addObject("titulo", pergunta.getId() != null ? "Editar Pergunta" : "Nova Pergunta");
            model.addObject("corridas", corridaService.listar());
            return model;
        }

        if (imagem != null && !imagem.isEmpty()) {
            try {
                if (pergunta.getId() != null) {
                    Pergunta existente = perguntaService.getPerguntaById(pergunta.getId());
                    if (existente.getImagemPath() != null) {
                        perguntaService.excluirImagem(existente.getImagemPath());
                    }
                }
                String caminho = perguntaService.salvarImagem(imagem);
                pergunta.setImagemPath(caminho);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("aviso",
                    "Pergunta salva, mas houve erro ao processar a imagem: " + e.getMessage());
            }
        } else {
            if (pergunta.getId() != null) {
                Pergunta existente = perguntaService.getPerguntaById(pergunta.getId());
                pergunta.setImagemPath(existente.getImagemPath());
            }
        }

        if (pergunta.getId() != null) {
            perguntaService.updatePergunta(pergunta.getId(), pergunta);
        } else {
            perguntaService.createPergunta(pergunta);
        }

        redirectAttributes.addFlashAttribute("mensagem", "Pergunta salva com sucesso!");
        return new ModelAndView("redirect:/perguntas");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView exibirFormularioEdicao(@PathVariable Long id, ModelAndView model) {
        model.setViewName("perguntas/formularioPergunta");
        Pergunta pergunta = perguntaService.getPerguntaById(id);

        model.addObject("pergunta", pergunta);
        model.addObject("titulo", "Editar Pergunta");
        model.addObject("corridas", corridaService.listar());
        return model;
    }

    @GetMapping("/{id}/excluir-imagem")
    @PreAuthorize("hasRole('ADMIN')")
    public String excluirImagem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Pergunta pergunta = perguntaService.getPerguntaById(id);
        if (pergunta.getImagemPath() != null && !pergunta.getImagemPath().isBlank()) {
            perguntaService.excluirImagem(pergunta.getImagemPath());
            pergunta.setImagemPath(null);
            perguntaService.updatePergunta(id, pergunta);
        }
        redirectAttributes.addFlashAttribute("mensagem", "Imagem removida com sucesso!");
        return "redirect:/perguntas/editar/" + id;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirPergunta(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Pergunta pergunta = perguntaService.getPerguntaById(id);
        if(pergunta.getImagemPath() != null){
            perguntaService.excluirImagem(pergunta.getImagemPath());
        }
        perguntaService.deletePerguntaById(id);

        redirectAttributes.addFlashAttribute("mensagem", "pergunta excluída com sucesso!");
        return new ModelAndView("redirect:/perguntas");
    }
}
