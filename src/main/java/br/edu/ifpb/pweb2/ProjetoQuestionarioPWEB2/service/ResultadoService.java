package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Corrida;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Participante;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.Resultado;
import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.ResultadoRepository;


@Service
public class ResultadoService {

    private static final Logger logger = LoggerFactory.getLogger(ResultadoService.class);

    private final ResultadoRepository resultadoRepository;

    public ResultadoService(ResultadoRepository resultadoRepository) {
        this.resultadoRepository = resultadoRepository;
    }

    /**
     * RN01
     * RN02
     */
    public Resultado salvarResultado(Participante participante, Corrida corrida, int pontuacaoFinal) {
        if (resultadoRepository.existsByParticipanteAndCorrida(participante, corrida)) {
            logger.info("Resultado já existente para participante {} na corrida {} - não persistido (RN02)",
                    participante.getId(), corrida.getId());
            return null;
        }

        BigDecimal pontuacao = BigDecimal.valueOf(pontuacaoFinal);

        Resultado resultado = new Resultado();
        resultado.setParticipante(participante);
        resultado.setCorrida(corrida);
        resultado.setPontuacao(pontuacao);
        resultado.setDataHora(LocalDateTime.now());

        Resultado salvo = resultadoRepository.save(resultado);
        logger.info("Resultado salvo - id: {}, participante: {}, corrida: {}, pontuação: {}",
                salvo.getId(), participante.getNome(), corrida.getTitulo(), pontuacao);
        return salvo;
    }

    public boolean resultadoExiste(Participante participante, Corrida corrida) {
        return resultadoRepository.existsByParticipanteAndCorrida(participante, corrida);
    }

    public Optional<Resultado> buscarUltimo(Participante participante, Corrida corrida) {
        return resultadoRepository.findFirstByParticipanteAndCorridaOrderByDataHoraDesc(participante, corrida);
    }

    // Backlog 22
    public Page<Resultado> buscarRankingGeral(Pageable pageable) {
        return resultadoRepository.findAllByOrderByPontuacaoDescDataHoraAsc(pageable);
    }

    // Backlog 23
    public Page<Resultado> buscarRankingPorCorrida(Corrida corrida, Pageable pageable) {
        return resultadoRepository.findByCorridaOrderByPontuacaoDescDataHoraAsc(corrida, pageable);
    }

    public boolean participanteTemPontuacao(Participante participante) {
        return resultadoRepository.existsByParticipante(participante);
    }

    public boolean participanteTemPontuacao(Participante participante, Corrida corrida) {
        return resultadoRepository.existsByParticipanteAndCorrida(participante, corrida);
    }

    // IDs das corridas que o participante já concluiu (usado no lobby)
    public Set<Long> getCorridasFeitasIds(Participante participante) {
        return resultadoRepository.findByParticipante(participante)
                .stream()
                .map(r -> r.getCorrida().getId())
                .collect(Collectors.toSet());
    }
}
