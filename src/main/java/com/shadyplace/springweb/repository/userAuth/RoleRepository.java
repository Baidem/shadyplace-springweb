package com.shadyplace.springweb.repository.userAuth;

import com.shadyplace.springweb.models.userAuth.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {


    Role findFirstByRoleName(String role);

    @Override
    public List<Role> findAll();

}
