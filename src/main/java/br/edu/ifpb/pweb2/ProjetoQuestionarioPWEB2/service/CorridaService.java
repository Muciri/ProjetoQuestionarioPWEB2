package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service;

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

    // Usado no lobby do participante (UC07) — só corridas ativas
    public List<Corrida> listarAtivas() {
        return repository.findByAtivaTrue().stream().filter(c -> c.getPerguntas() != null && !c.getPerguntas().isEmpty()).toList();
    }

    public Corrida salvar(Corrida corrida) {
        return repository.save(corrida);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Corrida buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corrida não encontrada: " + id));
    }


    public boolean corridaValida(Corrida corrida){
        if(corrida.getTempoSegundos() == null || corrida.getTempoSegundos() < 10){
            return false;
        }
        if (corrida.getPerguntas() == null || corrida.getPerguntas().isEmpty()) {
            return false;
        }
        return true;

        // vou colocar em um so if dps esses 2 ifs 
    }
}