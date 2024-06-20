package com.example.training.repository;


import com.example.training.entity.Templates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Templates, String> {
    Optional<Templates> findByCode(String code);

}
