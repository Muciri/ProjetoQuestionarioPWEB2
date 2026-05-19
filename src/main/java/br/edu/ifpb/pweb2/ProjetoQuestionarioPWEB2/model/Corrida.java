package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Corrida")
@Data
@NoArgsConstructor
public class Corrida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_corrida")
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Column(name="titulo", nullable = false)
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(name="descricao", nullable = false)
    private String descricao;

    @NotNull(message = "Tempo é obrigatório")
    @Min(value = 10, message = "Tempo mínimo é 10 segundos")
    @Column(name="tempo_segundos", nullable = false)
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