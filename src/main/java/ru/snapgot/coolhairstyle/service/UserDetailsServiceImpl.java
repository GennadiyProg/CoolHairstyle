package ru.snapgot.coolhairstyle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.snapgot.coolhairstyle.model.Role;
import ru.snapgot.coolhairstyle.model.User;
import ru.snapgot.coolhairstyle.repos.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(String.format("User %s doesn't exists", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getRole(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getRole(Role role){
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return roles;
    }
}
