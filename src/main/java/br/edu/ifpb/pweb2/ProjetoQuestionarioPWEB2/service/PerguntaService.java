package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception.CorridaNaoEncontradaException;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception.PerguntaNaoEncontradaException;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Pergunta;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.CorridaRepository;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.PerguntaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerguntaService {
    private PerguntaRepository perguntaRepository;
    private CorridaRepository corridaRepository;

    public PerguntaService(PerguntaRepository perguntaRepository, CorridaRepository corridaRepository) {
        this.perguntaRepository = perguntaRepository;
        this.corridaRepository = corridaRepository;
    }

    public List<Pergunta> getPerguntas() {
        return this.perguntaRepository.findAll();
    }
    public List<Pergunta> getPerguntasPorCorrida(Long corridaId) {
        return perguntaRepository.findByCorridaId(corridaId);
    }

    public List<Corrida> getCorridas() {
        return corridaRepository.findAll();
    }

    public Pergunta getPerguntaById(Long id) {
    return perguntaRepository.findById(id)
            .orElseThrow(() -> new PerguntaNaoEncontradaException(id));
}

    public Pergunta createPergunta(Pergunta pergunta) {
        return this.perguntaRepository.save(pergunta);
    }

    public Pergunta updatePergunta(Long id, Pergunta pergunta) {
    getPerguntaById(id);
    pergunta.setId(id);
    return perguntaRepository.save(pergunta);
}

    public void deletePerguntaById(Long id) {
    getPerguntaById(id);
    perguntaRepository.deleteById(id);
}
}
