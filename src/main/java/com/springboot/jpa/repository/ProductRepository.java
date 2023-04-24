package com.springboot.jpa.repository;

import com.springboot.jpa.entity.ProductJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductJpa,Long> {
    //CRUD 이미 구현되어잇음
}
