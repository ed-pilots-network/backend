package io.edpn.backend.user.application.service;

import io.edpn.backend.user.application.model.EdpnUserDetails;
import io.edpn.backend.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class EdpnUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(EdpnUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
