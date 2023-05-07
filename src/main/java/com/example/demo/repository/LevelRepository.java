package com.example.demo.repository;

import com.example.demo.entity.Level;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends CrudRepository<Level, Long> {
}
