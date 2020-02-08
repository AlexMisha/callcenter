package com.shepard.callcenter.dao;

import com.shepard.callcenter.entity.Client;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Alex Misha
 */
@RepositoryRestResource(path = "clients", collectionResourceRel = "clients", itemResourceRel = "client")
public interface ClientRepository extends CrudRepository<Client, Long>, JpaSpecificationExecutor<Client> {
}
