package io.edpn.backend.user.infrastructure.persistence.repostory;

import io.edpn.backend.user.domain.model.EdpnUser;
import io.edpn.backend.user.domain.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisUserRepository implements UserRepository {

    @Override
    public Optional<EdpnUser> findByUsername(String email) {
        return Optional.empty();
    }
}
