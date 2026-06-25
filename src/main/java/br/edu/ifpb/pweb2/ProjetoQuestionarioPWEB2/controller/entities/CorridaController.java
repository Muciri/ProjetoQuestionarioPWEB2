package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.CorridaService;
import jakarta.validation.Valid;


// UC01 - Administrador cadastra corrida
// UC02 - Administrador exclui corrida
// UC03 - Administrador modifica corrida
@Controller
@RequestMapping("/corridas")
public class CorridaController {

    @Autowired
    private CorridaService service;

    // -------------------------------------------------------
    // Listagem - GET /corridas
    // -------------------------------------------------------
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("corridas", service.listar());
        return "corridas/listaCorridas";
    }

    // -------------------------------------------------------
    // UC01 - exibe formulário de nova corrida - GET /corridas/nova
    // -------------------------------------------------------
    @GetMapping("/nova")
    public String formNova(Model model) {
        model.addAttribute("corrida", new Corrida());
        return "corridas/form";
    }

    // -------------------------------------------------------
    // UC03 - exibe formulário de edição - GET /corridas/{id}/editar
    // -------------------------------------------------------
    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Long id, Model model) {
        model.addAttribute("corrida", service.buscarPorId(id));
        return "corridas/form";
    }

    // -------------------------------------------------------
    // UC01 e UC03 - salva (novo ou editado) - POST /corridas/salvar
    //
    // Como distinguir novo de edição:
    //   - Novo:   corrida.getId() == null  (campo hidden vazio no form)
    //   - Editar: corrida.getId() != null
    //
    // IMPORTANTE: verificar isNova ANTES de chamar service.salvar(),
    // pois após salvar o JPA preenche o id mesmo em objetos novos.
    // -------------------------------------------------------
    @PostMapping("/salvar")
    @PreAuthorize("hasRole('ADMIN')")
    public String salvar(@Valid @ModelAttribute("corrida") Corrida corrida,BindingResult result, RedirectAttributes flash) {

        if (result.hasErrors()) {
            return "corridas/form";
        }

        boolean semPerguntas = true;

        if(corrida.getId() != null){
            Corrida banco = service.buscarPorId(corrida.getId());
            semPerguntas = banco.getPerguntas() == null || banco.getPerguntas().isEmpty();
        }

        // essa condicional vai negar a permissão de ativar a corrida sem perguntas
        if(Boolean.TRUE.equals(corrida.getAtiva()) && semPerguntas){
            corrida.setAtiva(false);
            flash.addFlashAttribute("aviso", "A corrida foi salva como INATIVA/Falsa pois ela ainda não possui perguntas. Por favor, cadastre as perguntas para ativar");
        }

        boolean isNova = (corrida.getId() == null);
        Corrida salva = service.salvar(corrida); // coloque isso para salvar dps da validação
        if (isNova) {
            // UC01: após cadastro, pergunt
            // a se quer cadastrar perguntas
            flash.addFlashAttribute("mensagem",
                    "Corrida \"" + salva.getTitulo() + "\" cadastrada com sucesso!");
            return "redirect:/corridas/" + salva.getId() + "/pos-cadastro";
        } else {
            // UC03: edição - volta para lista com mensagem
            flash.addFlashAttribute("mensagem",
                    "Corrida \"" + salva.getTitulo() + "\" atualizada com sucesso!");
            return "redirect:/corridas";
        }
    }

    // -------------------------------------------------------
    // UC01 - tela pós-cadastro: "Deseja cadastrar perguntas?"
    // GET /corridas/{id}/pos-cadastro
    // -------------------------------------------------------
    @GetMapping("/{id}/pos-cadastro")
    public String posCadastro(@PathVariable Long id, Model model) {
        model.addAttribute("corrida", service.buscarPorId(id));
        return "corridas/posCadastro"; // Corrigir aqui, pois era pos-cadastro, o correto é posCadastro
    }

    // -------------------------------------------------------
    // UC02 - exclui corrida - GET /corridas/{id}/excluir
    // -------------------------------------------------------
    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes flash) {
        Corrida corrida = service.buscarPorId(id);
        String titulo = corrida.getTitulo();
        service.excluir(id);
        flash.addFlashAttribute("mensagem", "Corrida \"" + titulo + "\" excluída com sucesso!");
        return "redirect:/corridas";
    }


    @GetMapping("/{id}")
    public String detaqlhes(@PathVariable Long id, Model model){
        Corrida corrida = service.buscarPorId(id);
        model.addAttribute("corrida", corrida);
        model.addAttribute("corridaValida", service.corridaValida(corrida));
        return "corridas/detalhesCorrida";
    }     


    
}