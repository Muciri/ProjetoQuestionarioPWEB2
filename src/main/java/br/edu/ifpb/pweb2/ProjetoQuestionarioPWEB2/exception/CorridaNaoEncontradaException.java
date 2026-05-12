package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Exceção lançada quando uma corrida solicitada não existe na base.
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Corrida não encontrada")
public class CorridaNaoEncontradaException extends RuntimeException {

    public CorridaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
