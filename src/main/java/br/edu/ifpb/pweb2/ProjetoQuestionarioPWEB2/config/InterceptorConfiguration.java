package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.config;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.interceptor.RotasAdministradorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Autowired
    RotasAdministradorInterceptor rotasAdministradorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rotasAdministradorInterceptor)
                .addPathPatterns("/perguntas/**");
                // Gabriel, acho massa depois adicionar o padrão: .addPathPatterns("/corridas/**");
                // pra redirecionar o usuário pra tela de login caso tente acessar as rotas de corridas sem ser admin
    }
}
