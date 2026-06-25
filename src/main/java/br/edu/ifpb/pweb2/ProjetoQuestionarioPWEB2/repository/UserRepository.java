package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
}
