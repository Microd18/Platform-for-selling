package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.repository.UserRepository;

import java.util.Collections;

/**
 * Реализация интерфейса UserDetailsService для получения информации о пользователе.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Загружает информацию о пользователе по его имени пользователя.
     *
     * @param username имя пользователя
     * @return информация о пользователе
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var user = userRepository.findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден: " + username));
        return new User(user.getLogin(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
    }
}