package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.CorridaService;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.PerguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/admin")
public class AdministradorCorridaController {



    @Autowired
    private CorridaService corridaService;



    @Autowired
    private PerguntaService perguntaService;

    @GetMapping
    public String dashboard(Model model){
        var corridas = corridaService.listar();
        var perguntas = perguntaService.getPerguntas();


        long totalAtivas = corridas.stream().filter(c -> Boolean.TRUE.equals(c.getAtiva())).count();

        model.addAttribute("corridas", corridas);
        model.addAttribute("perguntas", perguntas);
        model.addAttribute("totalCorridas", corridas.size());
        model.addAttribute("totalAtivas", totalAtivas);
        model.addAttribute("totalPerguntas", perguntas.size());

        return "admin/dashboard";
    }



}
