package com.dn.baomoiapi.service.serviceImpl;

import com.dn.baomoiapi.model.news;
import com.dn.baomoiapi.repository.NewsRepo;
import com.dn.baomoiapi.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepo newsRepo;

    @Override
    public List<news> findAll() {
        return newsRepo.findAll();
    }

    @Override
    public <S extends news> List<S> saveAll(Iterable<S> entities) {
        return newsRepo.saveAll(entities);
    }

    @Override
    public <S extends news> S save(S entity) {
        return newsRepo.save(entity);
    }

    public Optional<news> findById(Integer integer) {
        return newsRepo.findById(integer);
    }

    @Override
    public Page<news> findAll(Pageable pageable) {
        return newsRepo.findAll(pageable);
    }

    @Override
    @Query(value = "select top 5 *  from news", nativeQuery = true)
    public List<news> find5News() {
        return newsRepo.find5News();
    }

    @Override
    @Query(value = "SELECT * FROM news WHERE cateId = ?1", nativeQuery = true)
    public List<news> findByCateId(int cateId) {
        return newsRepo.findByCateId(cateId);
    }

    @Deprecated
    public news getOne(Integer integer) {
        return newsRepo.getOne(integer);
    }

    @Override
    public boolean existsById(int i) {
        return newsRepo.existsById(i);
    }

    @Override
    public void deleteById(int i) {
        newsRepo.deleteById(i);
    }
}
