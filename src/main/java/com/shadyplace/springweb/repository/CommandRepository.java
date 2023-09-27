package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Command;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends CrudRepository<Command, Long> {

    @Query(value = "FROM Command")
    Page<Command> paginationResult(Pageable pageable);

}
