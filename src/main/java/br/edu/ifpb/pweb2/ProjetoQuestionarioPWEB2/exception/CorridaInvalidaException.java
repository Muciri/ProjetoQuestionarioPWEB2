package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando o participante tenta iniciar uma corrida que não é válida
 * (RN03: sem perguntas, sem tempo, ou inativa).
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Corrida não pode ser iniciada")
public class CorridaInvalidaException extends RuntimeException {

    public CorridaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
