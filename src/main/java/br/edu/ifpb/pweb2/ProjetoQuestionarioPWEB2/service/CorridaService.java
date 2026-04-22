package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.CorridaRepository;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.*;

@Service
public class CorridaService {


    @Autowired
    private CorridaRepository repository;


    public List<Corrida> listar(){
        return repository.findAll();
    }

    public Corrida salvar(Corrida corrida) {
        return repository.save(corrida);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    // se num achar dentro do banco de dados o id ele vai lançar uma exceção pelo orElseThrow
    public Corrida buscarPorId(Long id){
        return repository.findById(id).orElseThrow();
    }

}
