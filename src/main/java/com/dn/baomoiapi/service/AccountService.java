package com.dn.baomoiapi.service;

import com.dn.baomoiapi.model.account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {
    List<account> findAll();

    <S extends account> S save(S entity);

    Optional<account> findById(int i);

    @Query(value = "SELECT * FROM account WHERE username = ?1 AND password = ?2", nativeQuery = true)
    Optional<account> findByUsernameAndPassword(String username, String password);

    boolean existsById(int i);

    void deleteById(int i);
}
