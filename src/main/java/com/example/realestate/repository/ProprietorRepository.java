package com.example.realestate.repository;

import com.example.realestate.domain.Proprietor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProprietorRepository extends JpaRepository<Proprietor,Long> {
    Proprietor getReferenceByIdAndActiveTrue(Long id);

    Optional<Proprietor> findByIdAndActiveTrue(Long id);


    Page<Proprietor> findByActiveTrue(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByIdAndActiveTrue(Long id);
}
