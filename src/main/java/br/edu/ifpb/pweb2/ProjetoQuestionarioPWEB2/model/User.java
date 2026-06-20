package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String username;
    private String password;
    private Boolean enabled;

    @OneToMany(mappedBy = "username")
    List<Authority> authorities;
}