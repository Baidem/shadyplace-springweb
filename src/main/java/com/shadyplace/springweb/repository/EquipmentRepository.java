package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Equipment;
import com.shadyplace.springweb.models.enums.EquipmentOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {

    Equipment findFirstByEquipmentOption(EquipmentOption option);
}
