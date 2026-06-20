package br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.service;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.model.User;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findById(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.getEnabled())
                .authorities(
                        user.getAuthorities()
                                .stream()
                                .map(a -> new SimpleGrantedAuthority(a.getAuthority()))
                                .collect(Collectors.toList())
                )
                .build();
    }
}