package com.dn.baomoiapi.service;

import com.dn.baomoiapi.model.news;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface NewsService {
    List<news> findAll();

    <S extends news> List<S> saveAll(Iterable<S> entities);

    <S extends news> S save(S entity);

    public Optional<news> findById(Integer integer);

    Page<news> findAll(Pageable pageable);

    @Query(value = "select top 5 *  from news", nativeQuery = true)
    List<news> find5News();



    @Query(value = "SELECT * FROM news WHERE cateId = ?1", nativeQuery = true)
    List<news> findByCateId(int cateId);

    public news getOne(Integer integer);

    boolean existsById(int i);

    void deleteById(int i);
}
