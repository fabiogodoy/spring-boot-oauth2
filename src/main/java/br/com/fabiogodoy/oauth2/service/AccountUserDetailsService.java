package br.com.fabiogodoy.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fabiogodoy.oauth2.repository.UserRepository;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    private UserRepository accountRepository;

    @Autowired
    public AccountUserDetailsService(UserRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository
                .findByUsername(username)
                .map(user -> new User(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER")))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
    }
}
