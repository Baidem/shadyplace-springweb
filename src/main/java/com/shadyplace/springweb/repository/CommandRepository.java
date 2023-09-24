package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Command;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends CrudRepository<Command, Long> {


}
