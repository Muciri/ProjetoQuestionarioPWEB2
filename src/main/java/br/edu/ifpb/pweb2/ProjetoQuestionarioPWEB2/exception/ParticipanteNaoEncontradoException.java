package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Participante não encontrado")
public class ParticipanteNaoEncontradoException extends RuntimeException {
    public ParticipanteNaoEncontradoException(String nome) {
        super("Participante " + nome + " não encontrado.");
    }
}