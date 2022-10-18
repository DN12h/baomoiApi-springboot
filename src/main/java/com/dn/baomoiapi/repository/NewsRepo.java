package com.dn.baomoiapi.repository;

import com.dn.baomoiapi.model.news;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepo extends JpaRepository<news, Integer> {
    @Query(value = "select top 5 *  from news",nativeQuery = true)
    List<news> find5News();

    @Query(value = "SELECT * FROM news WHERE cate_id = ?1",nativeQuery = true)
    List<news> findByCateId(int cateId);
}
