package com.dn.baomoiapi.repository;

import com.dn.baomoiapi.model.account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CookieValue;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<account,Integer> {
     @Query(value = "SELECT * FROM account WHERE username = ?1 AND password = ?2",nativeQuery = true)
     Optional<account> findByUsernameAndPassword(String username,String password);

}
