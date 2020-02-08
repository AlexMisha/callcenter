package com.shepard.callcenter.dao;

import com.shepard.callcenter.entity.Operator;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Alex Misha
 */
@RepositoryRestResource(path = "operators", collectionResourceRel = "operators", itemResourceRel = "operator")
public interface OperatorRepository extends CrudRepository<Operator, Long>, JpaSpecificationExecutor<Operator> {
}
