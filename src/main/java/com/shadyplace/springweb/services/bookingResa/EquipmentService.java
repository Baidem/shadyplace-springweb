package com.shadyplace.springweb.services.bookingResa;

import com.shadyplace.springweb.models.bookingResa.Equipment;
import com.shadyplace.springweb.repository.bookingResa.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    EquipmentRepository equipmentRepository;

    public Equipment findByOption(String option){
        return this.equipmentRepository.findFirstByOption(option);
    }

    public void save(Equipment equipment){
        this.equipmentRepository.save(equipment);
    }

    public List<Equipment> findAll(){
        return this.equipmentRepository.findAll();
    }
}
