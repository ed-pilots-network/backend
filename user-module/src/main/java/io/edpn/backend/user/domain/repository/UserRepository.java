package io.edpn.backend.user.domain.repository;

import io.edpn.backend.user.domain.model.EdpnUser;
import java.util.Optional;

public interface UserRepository {
    Optional<EdpnUser> findByUsername(String email);
}
