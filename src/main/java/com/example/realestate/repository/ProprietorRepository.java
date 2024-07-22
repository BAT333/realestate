package com.example.realestate.repository;

import com.example.realestate.domain.Proprietor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    @Query("""
            select p from Proprietor p where p.active = true and p.type = 'SURVEYOR'
            order by rand()
            limit 1
            """)
    Proprietor surveyor();
}
