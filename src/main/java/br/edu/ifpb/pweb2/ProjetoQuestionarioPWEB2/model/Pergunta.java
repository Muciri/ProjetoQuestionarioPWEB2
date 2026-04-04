package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "id_corrida", nullable = false)
    @JsonBackReference("corrida-pergunta")
    private Corrida corrida;
}
