package io.edpn.backend.user.application.model;

import io.edpn.backend.user.domain.model.EdpnUser;
import io.edpn.backend.user.domain.model.UserGrant;
import io.edpn.backend.user.domain.model.UserRole;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@RequiredArgsConstructor
public class EdpnUserDetails implements UserDetails {

    private final EdpnUser user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.concat(user.getRoles().stream().map(UserRole::getName).map("ROLE_"::concat),
                        Stream.concat(user.getRoles().stream().map(UserRole::getGrants).flatMap(Set::stream).map(UserGrant::getName),
                                user.getGrants().stream().map(UserGrant::getName).map("GRANT_"::concat)))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return Optional.ofNullable(user.getAccountExpiryTimestamp()).map(ts -> LocalDateTime.now().isBefore(ts)).orElse(false);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Optional.ofNullable(user.getPasswordExpiryTimestamp()).map(ts -> LocalDateTime.now().isBefore(ts)).orElse(false);
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
