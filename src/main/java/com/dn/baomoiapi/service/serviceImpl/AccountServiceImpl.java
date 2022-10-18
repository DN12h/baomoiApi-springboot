package com.dn.baomoiapi.service.serviceImpl;

import com.dn.baomoiapi.model.account;
import com.dn.baomoiapi.repository.AccountRepo;
import com.dn.baomoiapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    @Override
    public List<account> findAll() {
        return accountRepo.findAll();
    }

    @Override
    public <S extends account> S save(S entity) {
        return accountRepo.save(entity);
    }

    @Override
    public Optional<account> findById(int i) {
        return accountRepo.findById(i);
    }

    @Override
    @Query(value = "SELECT * FROM account WHERE username = ?1 AND password = ?2", nativeQuery = true)
    public Optional<account> findByUsernameAndPassword(String username, String password) {
        return accountRepo.findByUsernameAndPassword(username, password);
    }



    @Override
    public boolean existsById(int i) {
        return accountRepo.existsById(i);
    }

    @Override
    public void deleteById(int i) {
        accountRepo.deleteById(i);
    }
}
