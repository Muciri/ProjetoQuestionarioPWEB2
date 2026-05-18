package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Pergunta")
@Data
@NoArgsConstructor
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pergunta")
    private Long id;

    @NotBlank(message = "Enunciado é obrigatório")
    @Column(name="enunciado", nullable = false)
    private String enunciado;

    @NotNull(message = "Alternativas são obrigatórias")
    @ElementCollection
    @CollectionTable(
            name = "pergunta_alternativas",
            joinColumns = @JoinColumn(name = "id_pergunta")
    )
    @Column(name = "alternativa", nullable = false)
    private List<String> alternativas;

    @NotNull(message = "Resposta correta é obrigatória")
    @Min(value = 0, message = "Índice da resposta deve ser 0 ou maior")
    @Column(nullable = false)
    private Integer respostaCorreta;

    @NotNull(message = "Pontos são obrigatórios")
    @Min(value = 1, message = "Pontos mínimos é 1")
    @Column(nullable = false)
    private Integer pontos = 1;

    @ManyToOne
    @JoinColumn(name = "id_corrida", nullable = false)
    @JsonBackReference("corrida-pergunta")
    private Corrida corrida;
}
