package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.config;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.interceptor.RotasAdministradorInterceptor;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.interceptor.SessaoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    RotasAdministradorInterceptor rotasAdministradorInterceptor;

    @Autowired
    SessaoInterceptor sessaoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Protege rotas de admin (requer sessão com admin=true)
        registry.addInterceptor(rotasAdministradorInterceptor)
                .addPathPatterns("/perguntas/**", "/corridas/**", "/admin/**");

        // Protege rotas de participante (requer qualquer sessão ativa)
        registry.addInterceptor(sessaoInterceptor)
                .addPathPatterns("/lobby", "/lobby/**", "/corrida/**", "/ranking", "/ranking/**");
    }
}
