package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.interceptor;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RotasAdministradorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("/autenticacao/login");
            return false;
        }

        Object participanteSessao = session.getAttribute("participante");

        if (!(participanteSessao instanceof Participante participante)) {
            response.sendRedirect("/autenticacao/login");
            return false;
        }

        if (participante.getAdmin()) {
            return true;
        }

        response.sendRedirect("/");
        return false;
    }
}