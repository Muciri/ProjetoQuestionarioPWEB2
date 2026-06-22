package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Resultado;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    Page<Resultado> findAllByOrderByPontuacaoDescDataHoraAsc(Pageable pageable);

    Page<Resultado> findByCorridaOrderByPontuacaoDescDataHoraAsc(Corrida corrida, Pageable pageable);

    List<Resultado> findByParticipante(Participante participante);

    boolean existsByParticipante(Participante participante);

    boolean existsByParticipanteAndCorrida(Participante participante, Corrida corrida);

    Optional<Resultado> findFirstByParticipanteAndCorridaOrderByDataHoraDesc(Participante participante, Corrida corrida);
}
