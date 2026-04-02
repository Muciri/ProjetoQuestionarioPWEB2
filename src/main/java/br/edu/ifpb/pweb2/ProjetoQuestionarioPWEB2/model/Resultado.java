package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Resultado")
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_participante", nullable = false)
    @JsonBackReference("participante-resultado")
    private Participante participante;

    @ManyToOne
    @JoinColumn(name = "id_corrida", nullable = false)
    @JsonBackReference("corrida-resultado")
    private Corrida corrida;

    @Column(name="pontuacao", nullable = false)
    private BigDecimal pontuacao;

    @Column(name="dataHora", nullable = false)
    private LocalDateTime dataHora;
}
