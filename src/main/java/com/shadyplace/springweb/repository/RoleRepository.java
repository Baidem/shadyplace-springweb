package com.shadyplace.springweb.repository;

import com.shadyplace.springweb.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {


    Role findFirstByRoleName(String role);
}
