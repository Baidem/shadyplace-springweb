package com.shadyplace.springweb.repository.bookingResa;

import com.shadyplace.springweb.models.bookingResa.Line;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepository extends CrudRepository<Line, Long> {

    @Override
    List<Line> findAll();

    Line findFirstByLabel(String lineLabel);

    Line getById(Long id);

}
