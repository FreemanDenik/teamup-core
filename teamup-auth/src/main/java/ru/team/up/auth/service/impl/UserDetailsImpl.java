package ru.team.up.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Account;
import ru.team.up.core.repositories.AdminRepository;
import ru.team.up.core.repositories.ModeratorRepository;
import ru.team.up.core.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailsImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ModeratorRepository moderatorRepository;

    @Autowired
    public UserDetailsImpl(UserRepository userRepository, AdminRepository adminRepository, ModeratorRepository moderatorRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.moderatorRepository = moderatorRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account userDetailsAccount = userRepository.findByEmail(email) == null ?
                adminRepository.findByEmail(email) == null ?
                        moderatorRepository.findByEmail(email) == null ? null
                                : moderatorRepository.findByEmail(email)
                        : adminRepository.findByEmail(email)
                : userRepository.findByEmail(email);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        log.info("Account authorization:{}", email);
        if (userDetailsAccount == null) {
            throw new UsernameNotFoundException("userDetailsAccount is null");
        } else {
            userDetailsAccount.setLastAccountActivity(LocalDateTime.now());
            return userDetailsAccount;
        }
    }
}
