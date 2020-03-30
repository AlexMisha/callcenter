package com.shepard.callcenter.dao;

import com.shepard.callcenter.entity.CDR;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Alex Misha
 */
@RepositoryRestResource(path = "cdr", collectionResourceRel = "cdr", itemResourceRel = "cdr")
public interface CDRRepository extends CrudRepository<CDR, Long>, JpaSpecificationExecutor<CDR> {
}
