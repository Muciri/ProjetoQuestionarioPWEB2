package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.exception.CorridaNaoEncontradaException;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.CorridaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorridaService {

    @Autowired
    private CorridaRepository repository;


    public List<Corrida> listar() {
        return repository.findAll();
    }

    // Usado no lobby do participante (UC07) - só corridas ativas
    public List<Corrida> listarAtivas() {
        return repository.findByAtivaTrue().stream().filter(c -> c.getPerguntas() != null && !c.getPerguntas().isEmpty()).toList();
    }

    public Corrida salvar(Corrida corrida) {
        if(corrida.getId() == null) {
            if(corrida.getAtiva() == null) corrida.setAtiva(false);
            return repository.save(corrida);
        }

        

        // criei esse objeto existente para salvar so os campos do forms sem tocar 
        // nas perguntas e os resultados que estão no banco
        Corrida existente = buscarPorId(corrida.getId());
        existente.setTitulo(corrida.getTitulo());
        existente.setDescricao(corrida.getDescricao());
        existente.setTempoSegundos(corrida.getTempoSegundos());
        existente.setAtiva(corrida.getAtiva());
        return repository.save(existente);
    }

    public void excluir(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    public Corrida buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CorridaNaoEncontradaException(id));
    }

    public boolean corridaValida(Corrida corrida){
        return Boolean.TRUE.equals(corrida.getAtiva())
            && corrida.getTempoSegundos() != null
            && corrida.getTempoSegundos() >= 10
            && corrida.getPerguntas() != null
            && !corrida.getPerguntas().isEmpty();
    }
}