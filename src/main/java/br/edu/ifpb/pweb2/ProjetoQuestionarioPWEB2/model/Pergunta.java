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

    @Column(name="enunciado", nullable = false)
    private String enunciado;

    @ElementCollection
    @CollectionTable(
            name = "pergunta_alternativas",
            joinColumns = @JoinColumn(name = "id_pergunta")
    )
    @Column(name = "alternativa", nullable = false)
    private List<String> alternativas;

    @Column(nullable = false)
    private Integer respostaCorreta;

    @Column(nullable = false)
    private Integer pontos = 1;

    @ManyToOne
    @JoinColumn(name = "id_corrida", nullable = false)
    @JsonBackReference("corrida-pergunta")
    private Corrida corrida;
}
