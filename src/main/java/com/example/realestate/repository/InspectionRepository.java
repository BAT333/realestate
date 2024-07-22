package com.example.realestate.repository;

import com.example.realestate.domain.ContractStatusInspection;
import com.example.realestate.domain.PropertyInspection;
import com.example.realestate.domain.Proprietor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InspectionRepository extends JpaRepository<PropertyInspection,Long> {
    boolean existsByBuyerAndStatus(Proprietor proprietor, ContractStatusInspection contractStatusInspection);
}
