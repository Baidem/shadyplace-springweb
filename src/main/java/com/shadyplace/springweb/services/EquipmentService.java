package com.shadyplace.springweb.services;

import com.shadyplace.springweb.models.Equipment;
import com.shadyplace.springweb.models.enums.EquipmentOption;
import com.shadyplace.springweb.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService {

    @Autowired
    EquipmentRepository equipmentRepository;

    public Equipment findByEquipmentOption(EquipmentOption option){
        return this.equipmentRepository.findFirstByEquipmentOption(option);
    }

    public void save(Equipment equipment){
        this.equipmentRepository.save(equipment);
    }

}
