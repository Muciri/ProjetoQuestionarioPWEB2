package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
}
