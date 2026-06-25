package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorridaRepository extends JpaRepository<Corrida, Long> {

    // Usado em listarAtivas() - lobby do participante (UC07)
    List<Corrida> findByAtivaTrue();
}