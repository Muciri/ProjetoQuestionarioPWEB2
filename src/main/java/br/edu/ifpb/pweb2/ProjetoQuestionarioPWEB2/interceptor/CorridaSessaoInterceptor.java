package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.interceptor;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service.SessaoCorridaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

@Component
public class CorridaSessaoInterceptor implements HandlerInterceptor {

    private final SessaoCorridaService sessaoCorridaService;

    public CorridaSessaoInterceptor(SessaoCorridaService sessaoCorridaService) {
        this.sessaoCorridaService = sessaoCorridaService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        boolean temCorrida = session != null && sessaoCorridaService.corridaAtiva(session) != null;

        if (!temCorrida) {
            FlashMap flash = new FlashMap();
            flash.put("erro", "Você precisa iniciar uma corrida primeiro.");
            RequestContextUtils.getFlashMapManager(request).saveOutputFlashMap(flash, request, response);
            response.sendRedirect(request.getContextPath() + "/lobby");
            return false;
        }
        return true;
    }
}
