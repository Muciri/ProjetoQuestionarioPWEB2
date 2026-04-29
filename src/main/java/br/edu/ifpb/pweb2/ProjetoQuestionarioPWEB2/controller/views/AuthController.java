package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.ParticipanteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autenticacao")
public class AuthController {
    private final ParticipanteService participanteService;

    public AuthController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro(ModelAndView model) {
        model.setViewName("/autenticacao/cadastro");
        model.addObject("participante", new Participante());
        return model;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute Participante participante, RedirectAttributes redirectAttributes) {
        this.participanteService.createParticipante(participante);

        redirectAttributes.addFlashAttribute("mensagem", "participante salvo!");
        return new ModelAndView("redirect:/autenticacao/login");
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView model) {
        model.setViewName("/autenticacao/login");
        model.addObject("participante", new Participante());
        return model;
    }

    @PostMapping("/logar")
    public ModelAndView logar(@ModelAttribute Participante participante, ModelAndView model, RedirectAttributes redirectAttributes, HttpSession session) {
        if(participanteValido(participante)) {
            redirectAttributes.addFlashAttribute("mensagem", "usuário autenticado com sucesso!");
            session.setAttribute("participante", this.participanteService.findByNome(participante.getNome()));
            model.setViewName("redirect:/");
        }
        else {
            redirectAttributes.addFlashAttribute("erro", "credenciais inválidas!");
            model.setViewName("redirect:/autenticacao/login");
        }

        return model;
    }

    @PostMapping("/logout")
    public ModelAndView logout(HttpSession session, ModelAndView model) {
        session.invalidate();

        model.setViewName("redirect:/autenticacao/login");
        return model;
    }

    private boolean participanteValido(Participante participante) {
        Participante participanteBD = this.participanteService.findByNome(participante.getNome());

        boolean valido = false;

        if(participanteBD != null) {
            valido = true;
        }

        return valido;
    }
}
