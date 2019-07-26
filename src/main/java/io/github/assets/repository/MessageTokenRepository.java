package io.github.assets.repository;

import io.github.assets.domain.MessageToken;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MessageToken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageTokenRepository extends JpaRepository<MessageToken, Long> {

}
