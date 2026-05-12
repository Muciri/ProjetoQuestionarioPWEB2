package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Backlog 4 — Interceptor que bloqueia rotas internas (lobby, corrida, ranking)
 * caso não haja participante autenticado na sessão.
 */
@Component
public class SessaoInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SessaoInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("participante") == null) {
            logger.info("Acesso bloqueado a {} — sessão inválida ou inexistente",
                    request.getRequestURI());
            response.sendRedirect(request.getContextPath() + "/autenticacao/login");
            return false;
        }

        return true;
    }
}
