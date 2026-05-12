package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.controller.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception.CorridaInvalidaException;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception.CorridaNaoEncontradaException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Tratamento GLOBAL de exceções
 *
 * Captura exceções não tratadas por nenhum controlador específico e direciona
 * para o template error.html, evitando a "Whitelabel Error Page" do Spring.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CorridaNaoEncontradaException.class)
    public ModelAndView handleCorridaNaoEncontrada(HttpServletRequest req,
                                                   CorridaNaoEncontradaException ex) {
        logger.warn("Corrida não encontrada — URL: {}, mensagem: {}",
                req.getRequestURI(), ex.getMessage());
        return montarErro(req, ex, "Corrida não encontrada");
    }

    @ExceptionHandler(CorridaInvalidaException.class)
    public ModelAndView handleCorridaInvalida(HttpServletRequest req,
                                              CorridaInvalidaException ex) {
        logger.warn("Tentativa de iniciar corrida inválida — URL: {}, mensagem: {}",
                req.getRequestURI(), ex.getMessage());
        return montarErro(req, ex, "Corrida inválida");
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(HttpServletRequest req, RuntimeException ex) {
        logger.error("Erro inesperado durante a requisição {}: {}",
                req.getRequestURI(), ex.getMessage(), ex);
        return montarErro(req, ex, "Erro inesperado no sistema");
    }

    private ModelAndView montarErro(HttpServletRequest req, Exception ex, String titulo) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("error", titulo);
        model.addObject("message", ex.getMessage());
        model.addObject("path", req.getRequestURI());
        model.addObject("exception", ex);
        return model;
    }
}
