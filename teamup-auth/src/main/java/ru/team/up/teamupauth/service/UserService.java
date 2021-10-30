package ru.team.up.teamupauth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.teamupauth.repository.UserRepository;
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findUserAndRolesByName (s);
    }
}
