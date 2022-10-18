package com.dn.baomoiapi.service.serviceImpl;

import com.dn.baomoiapi.model.category;
import com.dn.baomoiapi.repository.CategoryRepo;
import com.dn.baomoiapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private  CategoryRepo categoryRepo;

    @Override
    public List<category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public <S extends category> S save(S entity) {
        return categoryRepo.save(entity);
    }

    @Override
    public Page<category> findAll(Pageable pageable) {
        return categoryRepo.findAll(pageable);
    }

    @Override
    public Optional<category> findById(int i) {
        return categoryRepo.findById(i);
    }

    @Override
    public boolean existsById(int i) {
        return categoryRepo.existsById(i);
    }

    @Override
    public void deleteById(int i) {
        categoryRepo.deleteById(i);
    }
}
