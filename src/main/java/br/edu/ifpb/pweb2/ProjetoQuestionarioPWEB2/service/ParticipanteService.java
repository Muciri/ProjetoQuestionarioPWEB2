package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception.ParticipanteNaoEncontradoException;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.ParticipanteRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipanteService {
    private final ParticipanteRepository participanteRepository;

    public ParticipanteService(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    public Participante createParticipante(Participante participante) {
        return this.participanteRepository.save(participante);
    }

    public Participante findByNome(String nome) {
        return this.participanteRepository.findByNome(nome).orElseThrow(() -> new ParticipanteNaoEncontradoException(nome));
    }
}
