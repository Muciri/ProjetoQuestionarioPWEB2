package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Resposta inválida")
public class RespostaInvalidaException extends RuntimeException {
    public RespostaInvalidaException() {
        super("A alternativa selecionada é inválida.");
    }
}