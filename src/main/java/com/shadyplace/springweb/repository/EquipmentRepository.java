package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Equipment;
import com.shadyplace.springweb.models.enums.EquipmentOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {

    Equipment findFirstByEquipmentOption(EquipmentOption option);

    List<Equipment> getAll();

    Equipment getById(Long id);
}
