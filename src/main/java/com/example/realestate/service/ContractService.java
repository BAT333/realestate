package com.example.realestate.service;

import com.example.realestate.config.exceptions.ContractExceptions;
import com.example.realestate.model.DataRentDTO;
import com.example.realestate.domain.*;
import com.example.realestate.model.DataContract;
import com.example.realestate.model.DataContractUpdateDTO;
import com.example.realestate.repository.ContractRepository;
import com.example.realestate.repository.InspectionRepository;
import com.example.realestate.repository.PropertieRepository;
import com.example.realestate.repository.ProprietorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContractService {
    @Autowired
    private ContractRepository repository;
    @Autowired
    private PropertieRepository propertieRepository;
    @Autowired
    private ProprietorRepository proprietorRepository;
    @Autowired
    private InspectionRepository inspectionRepository;

    public DataContract registerContractSale(Long buyer, Long purchase) {
        var propertie = propertieRepository.findByIdAndActiveTrueAndStatus(purchase, ContractStatus.AVAILABLE);
        var proprietor = proprietorRepository.findByIdAndActiveTrue(buyer);
        if(propertie.isEmpty() || proprietor.isEmpty()){
            throw new ContractExceptions();
        }else{
            if(!inspectionRepository.existsByBuyerAndStatus(proprietor.get(), ContractStatusInspection.ACCOMPLISHED)){
                this.contractInspection(propertie.get(),proprietor.get());
                //ver se vai
                throw new ContractExceptions();
            }
                var contract =  repository.save(new Contract(propertie.get(),proprietor.get(), ContractType.SELL));
                propertie.get().contract(proprietor.get(),ContractType.SELL);
                return new DataContract(contract);
        }
    }

    private void contractInspection(Propertie propertie, Proprietor proprietor) {
        var surveyor = this.proprietorRepository.surveyor();
        this.inspectionRepository.save(new PropertyInspection(propertie,proprietor,surveyor));

    }

    public DataContract registerContractRent(Long buyer, Long purchase, @Valid DataRentDTO dto) {
        var propertie = propertieRepository.findByIdAndActiveTrueAndStatus(purchase, ContractStatus.AVAILABLE);
        var proprietor = proprietorRepository.findByIdAndActiveTrue(buyer);
        if(propertie.isEmpty() || proprietor.isEmpty()){
            throw new ContractExceptions();
        }else{
            if(!inspectionRepository.existsByBuyerAndStatus(proprietor.get(), ContractStatusInspection.ACCOMPLISHED)){
                this.contractInspection(propertie.get(),proprietor.get());
                //ver se vai
                throw new ContractExceptions();
            }
            var contract =  repository.save(new Contract(propertie.get(),proprietor.get(), ContractType.RENT));
            propertie.get().contract(proprietor.get(),ContractType.RENT);
            return new DataContract(contract);
        }
    }

    public DataContract registerContractSeason(Long buyer, Long purchase, @Valid DataRentDTO dto) {
        var propertie = propertieRepository.findByIdAndActiveTrueAndStatus(purchase, ContractStatus.AVAILABLE);
        var proprietor = proprietorRepository.findByIdAndActiveTrue(buyer);
        if(propertie.isEmpty() || proprietor.isEmpty()){
            throw new ContractExceptions();
        }else{
            if(!inspectionRepository.existsByBuyerAndStatus(proprietor.get(), ContractStatusInspection.ACCOMPLISHED)){
                this.contractInspection(propertie.get(),proprietor.get());
                //ver se vai
                throw new ContractExceptions();
            }
            var contract =  repository.save(new Contract(propertie.get(),proprietor.get(), ContractType.TEMPORARY));
            propertie.get().contract(proprietor.get(),ContractType.TEMPORARY);
            return new DataContract(contract);
        }
    }

    public Page<DataContract> list(Pageable pageable) {
        return this.repository.findByActiveTrue(pageable).map(DataContract::new);
    }

    public DataContract update(Long id, DataContractUpdateDTO dto) {
        var contract =  this.repository.findByIdAndActiveTrue(id);
        if(contract.isPresent()){
            contract.get().update(dto);
            return contract.map(DataContract::new).get();
        }
        throw new ContractExceptions();

    }

    public void delete(Long id) {
        var contract =  this.repository.findByIdAndActiveTrue(id);
        if(contract.isPresent()){
            contract.get().delete();
        }else{
            throw new ContractExceptions();
        }
    }

    public String updateSurvey(Long id, DataContractUpdateDTO dto) {
        var inspection = this.inspectionRepository.findById(id);
        if(inspection.isPresent()){
            inspection.get().updete(dto);
            return "INSPECTION CARRIED OUT";
        }else {
            throw new ContractExceptions();
        }
    }
}
