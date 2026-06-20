package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Authority;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.User;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.AuthorityRepository;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.ParticipanteRepository;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autenticacao")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final ParticipanteRepository participanteRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/cadastro")
    public ModelAndView cadastro(ModelAndView model) {

        model.addObject("user", new User());
        model.setViewName("autenticacao/cadastro");

        return model;
    }

    @GetMapping("/login")
    public String login() {
        return "autenticacao/login";
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) boolean isAdmin,
            RedirectAttributes redirectAttributes) {

        if (userRepository.existsById(username)) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Usuário já existe."
            );

            return new ModelAndView("redirect:/autenticacao/cadastro");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);

        userRepository.save(user);

        Authority authority = new Authority();
        authority.setId(new Authority.AuthorityId(username, "ROLE_PARTICIPANTE"));
        authorityRepository.save(authority);

        // ROLE se tiver marcado que é admin
        if (isAdmin) {
            Authority adminAuth = new Authority();
            adminAuth.setId(new Authority.AuthorityId(username, "ROLE_ADMIN"));
            authorityRepository.save(adminAuth);
        }

        Participante participante = new Participante();
        participante.setNome(username);
        participante.setEmail(email);
        participante.setAdmin(isAdmin);
        participante.setUser(user);

        participanteRepository.save(participante);

        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Usuário cadastrado com sucesso!"
        );

        return new ModelAndView("redirect:/autenticacao/login");
    }
}