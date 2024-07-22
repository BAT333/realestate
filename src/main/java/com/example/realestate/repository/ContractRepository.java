package com.example.realestate.repository;

import com.example.realestate.domain.Contract;
import com.example.realestate.model.DataContract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Long> {


    Optional<Contract> findByIdAndActiveTrue(Long id);

    Page<Contract> findByActiveTrue(Pageable pageable);
}
