package com.shepard.callcenter.dao;

import com.shepard.callcenter.entity.Client;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Alex Misha
 */
@RepositoryRestResource(path = "clients", collectionResourceRel = "clients", itemResourceRel = "client")
public interface ClientRepository extends CrudRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    List<Client> findByDoNotCallIsNullOrDoNotCallIsFalse();

    List<Client> findByDoNotCallIsNullOrDoNotCallIsFalseAndPhoneIn(Iterable<String> phones);

    List<Client> findByDoNotCallIsNullOrDoNotCallIsFalseAndPhoneNotIn(Iterable<String> phones);

    List<Client> findAll();
}
