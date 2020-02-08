package com.shepard.callcenter.dao;

import com.shepard.callcenter.entity.OperatorPassword;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Alex Misha
 */
@RepositoryRestResource(exported = false)
public interface OperatorPasswordRepository extends CrudRepository<OperatorPassword, Long>, JpaSpecificationExecutor<OperatorPassword> {
}
