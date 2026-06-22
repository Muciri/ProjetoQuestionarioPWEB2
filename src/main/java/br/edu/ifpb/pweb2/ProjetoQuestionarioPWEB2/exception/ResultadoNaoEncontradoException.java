package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resultado não encontrado")
public class ResultadoNaoEncontradoException extends RuntimeException {
    public ResultadoNaoEncontradoException(Long participanteId, Long corridaId) {
        super("Resultado não encontrado para o participante "
                + participanteId + " na corrida " + corridaId + ".");
    }
}