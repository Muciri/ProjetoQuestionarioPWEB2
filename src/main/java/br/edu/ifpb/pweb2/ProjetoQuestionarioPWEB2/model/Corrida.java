package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Corrida")
@Data
@NoArgsConstructor
public class Corrida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_corrida")
    private Long id;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @Column(name="tempoSegundos", nullable = false)
    private Integer tempoSegundos;

    @Column(name="ativa", nullable = false)
    private Boolean ativa;

    @ManyToOne()
    @JsonBackReference
    private Participante participante;

    @OneToMany(mappedBy = "corrida", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("corrida-pergunta")
    private List<Pergunta> perguntas;

    @OneToMany(mappedBy = "corrida", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("corrida-resultado")
    private List<Resultado> resultados;
}
