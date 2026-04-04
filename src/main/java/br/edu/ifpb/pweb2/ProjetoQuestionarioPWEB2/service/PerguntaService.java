package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Pergunta;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.PerguntaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerguntaService {
    private PerguntaRepository perguntaRepository;

    public PerguntaService(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    public List<Pergunta> getPerguntas() {
        return this.perguntaRepository.findAll();
    }

    public Pergunta getPerguntaById(Long id) {
        return this.perguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("pergunta não encontrada")); //depois substituir por notFoundException
    }

    public Pergunta createPergunta(Pergunta pergunta) {
        return this.perguntaRepository.save(pergunta);
    }

    public Pergunta UpdatePergunta(Long id, Pergunta pergunta) {
        if(!perguntaRepository.existsById(id)) {
            throw new RuntimeException("pergunta não encontrada"); //depois substituir por notFoundException
        }
        pergunta.setId(id);
        return this.perguntaRepository.save(pergunta);
    }

    public void deletePerguntaById(Long id) {
        if(!perguntaRepository.existsById(id)) {
            throw new RuntimeException("pergunta não encontrada"); //depois substituir por notFoundException
        }
        perguntaRepository.deleteById(id);
    }
}
