package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    //Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
