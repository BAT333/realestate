package com.example.realestate.service;

import com.example.realestate.config.exceptions.ContractExceptions;
import com.example.realestate.domain.Contract;
import com.example.realestate.domain.ContractStatus;
import com.example.realestate.domain.ContractType;
import com.example.realestate.model.DataContract;
import com.example.realestate.model.DataContractUpdateDTO;
import com.example.realestate.repository.ContractRepository;
import com.example.realestate.repository.PropertieRepository;
import com.example.realestate.repository.ProprietorRepository;
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

    public DataContract registerContractSale(Long buyer, Long purchase) {
        var propertie = propertieRepository.findByIdAndActiveTrueAndStatus(purchase, ContractStatus.AVAILABLE);
        var proprietor = proprietorRepository.findByIdAndActiveTrue(buyer);
        if(propertie.isEmpty() || proprietor.isEmpty()){
            throw new ContractExceptions();
        }else{
            var contract =  repository.save(new Contract(propertie.get(),proprietor.get(), ContractType.SELL,"SELL"));
            propertie.get().contract(proprietor.get(),ContractType.SELL);
            return new DataContract(contract);
        }
    }

    public DataContract registerContractRent(Long buyer, Long purchase) {
        var propertie = propertieRepository.findByIdAndActiveTrueAndStatus(purchase, ContractStatus.AVAILABLE);
        var proprietor = proprietorRepository.findByIdAndActiveTrue(buyer);
        if(propertie.isEmpty() || proprietor.isEmpty()){
            throw new ContractExceptions();
        }else{
            var contract =  repository.save(new Contract(propertie.get(),proprietor.get(), ContractType.RENT,"2 YEARS"));
            propertie.get().contract(proprietor.get(),ContractType.RENT);
            return new DataContract(contract);
        }
    }

    public DataContract registerContractSeason(Long buyer, Long purchase) {
        var propertie = propertieRepository.findByIdAndActiveTrueAndStatus(purchase, ContractStatus.AVAILABLE);
        var proprietor = proprietorRepository.findByIdAndActiveTrue(buyer);
        if(propertie.isEmpty() || proprietor.isEmpty()){
            throw new ContractExceptions();
        }else{
            var contract =  repository.save(new Contract(propertie.get(),proprietor.get(), ContractType.TEMPORARY,"15 DAYS"));
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
}
