package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Line;
import com.shadyplace.springweb.models.enums.LineLabel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends CrudRepository<Line, Long> {

    Line findByLabel(LineLabel lineLabel);

    Line getById(Long id);

}
