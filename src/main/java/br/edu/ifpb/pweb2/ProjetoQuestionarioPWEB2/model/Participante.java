package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Participante")
@Data
@NoArgsConstructor
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_participante")
    private Long id;

    @Column(name="nome", nullable = false)
    private String nome;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="admin", nullable = false)
    private Boolean admin;

    @OneToMany(mappedBy = "participante")
    @JsonManagedReference
    private List<Corrida> corridasFeitas;

    @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("participante-resultado")
    private List<Resultado> resultados;
}
