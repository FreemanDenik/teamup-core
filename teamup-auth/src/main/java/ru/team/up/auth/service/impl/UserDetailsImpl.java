package ru.team.up.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Account;
import ru.team.up.core.repositories.AccountRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service ("userDetailsImpl")
@Slf4j
public class UserDetailsImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public UserDetailsImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account userDetailsAccount = accountRepository.findByEmail(email);
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
