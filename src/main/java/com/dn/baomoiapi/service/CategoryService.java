package com.dn.baomoiapi.service;

import com.dn.baomoiapi.model.category;
import com.dn.baomoiapi.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    List<category> findAll();

    <S extends category> S save(S entity);

    Optional<category> findById(int i);

    boolean existsById(int i);
    public Page<category> findAll(Pageable pageable) ;

    void deleteById(int i);

}
