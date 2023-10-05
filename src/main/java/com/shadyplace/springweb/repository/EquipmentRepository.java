package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Equipment;
import com.shadyplace.springweb.models.enums.EquipmentOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {

    Equipment findFirstByOption(String option);

    List<Equipment> findAll();
    Equipment getById(Long id);
}
