package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Pergunta não encontrada")
public class PerguntaNaoEncontradaException extends RuntimeException {
    public PerguntaNaoEncontradaException(Long id) {
        super("Pergunta com id " + id + " não encontrada.");
    }
}