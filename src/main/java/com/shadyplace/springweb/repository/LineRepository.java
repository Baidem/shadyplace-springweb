package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Line;
import com.shadyplace.springweb.models.enums.LineLabel;
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
