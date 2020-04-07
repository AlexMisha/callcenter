package com.shepard.callcenter.security;

import com.shepard.callcenter.dao.OperatorPasswordRepository;
import com.shepard.callcenter.dao.OperatorRepository;
import java.util.Collections;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Alex Misha
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final OperatorRepository operatorRepository;
    private final OperatorPasswordRepository operatorPasswordRepository;

    public UserDetailsServiceImpl(OperatorRepository operatorRepository, OperatorPasswordRepository operatorPasswordRepository) {
        this.operatorRepository = operatorRepository;
        this.operatorPasswordRepository = operatorPasswordRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var operator = operatorRepository.findByLogin(username);
        if (operator == null) {
            throw new UsernameNotFoundException(username);
        }

        final var password = operatorPasswordRepository.findByOperator(operator);
        if (password == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(operator.getLogin(), password.getPassword(), Collections.emptyList());
    }
}
