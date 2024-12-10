package org.aoptut.repository;

import org.aoptut.entities.BlackListedToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListedTokenRepository extends CrudRepository<BlackListedToken, Integer> {
    BlackListedToken findByToken(String token);
}
