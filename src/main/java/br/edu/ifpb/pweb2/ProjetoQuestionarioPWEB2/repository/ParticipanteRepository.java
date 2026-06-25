package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    public Optional<Participante> findByNome(String nome);

    boolean existsByEmail(String email);
}
