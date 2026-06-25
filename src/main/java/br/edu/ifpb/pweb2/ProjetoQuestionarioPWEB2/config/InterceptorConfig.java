package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.config;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.interceptor.CorridaSessaoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final CorridaSessaoInterceptor corridaSessaoInterceptor;

    public InterceptorConfig(CorridaSessaoInterceptor corridaSessaoInterceptor) {
        this.corridaSessaoInterceptor = corridaSessaoInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corridaSessaoInterceptor)
                .addPathPatterns("/corrida/*/pergunta/*", "/corrida/*/responder");
    }
}
