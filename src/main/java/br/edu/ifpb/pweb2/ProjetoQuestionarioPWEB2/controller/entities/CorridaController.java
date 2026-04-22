package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.CorridaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.*;




// Aqui implementando toda a logica da UC01 UC02 UC03 do projeto, 
// o qual dentro da UC01 eu fiz a implementação da exibição do 
// formulario para a corrida nova,  o qual fiz pelo metodo formCorrida, 
// salvar nova corrida pelo metodo salvar, 
@Controller
@RequestMapping("/corridas")
public class CorridaController {
    @Autowired
    private CorridaService service;

    @GetMapping("/nova")
    public String formCorrida(Model model) {
        model.addAttribute("corrida",  new Corrida());
        return "corrida-form";
    }


    @GetMapping
    public String listar(Model model) {
        model.addAttribute("corridas", service.listar());
        return "corridas/listarCorridas";
    }

    @GetMapping("/editar/{id}")
    public String formEditar(@PathVariable Long id, Model model) {
        model.addAttribute("corrida", service.buscarPorId(id));
        return "corridas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes flash){
        service.excluir(id);
        flash.addFlashAttribute("mensagem", "Corrida excluída com sucesso!!");
        return "redirect:/corridas";
    }


    @PostMapping
    public String salvar(@ModelAttribute Corrida corrida, RedirectAttributes  flash){
        service.salvar(corrida);
        flash.addFlashAttribute("mensagem", "Corrida cadastrada com sucesso!");
        return "redirect:/corridas";
    }


    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Corrida corrida, RedirectAttributes flash){
        corrida.setId(id);
        service.salvar(corrida);
        flash.addFlashAttribute("mensagem", "Corrida atualizada com sucesso!");
        return "redirect:/corridas";


    }
    

}
