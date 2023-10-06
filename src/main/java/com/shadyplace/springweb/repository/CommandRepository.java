package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Command;
import com.shadyplace.springweb.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends CrudRepository<Command, Long> {

    @Query(value = "FROM Command")
    Page<Command> paginationResult(Pageable pageable);

    List<Command> findByUser(User user);

    List<Command> getCommandByUser(User user);

}
