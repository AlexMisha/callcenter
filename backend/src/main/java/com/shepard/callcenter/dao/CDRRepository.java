package com.shepard.callcenter.dao;

import com.shepard.callcenter.entity.CDR;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Alex Misha
 */
@RepositoryRestResource(path = "cdr", collectionResourceRel = "cdr", itemResourceRel = "cdr")
public interface CDRRepository extends CrudRepository<CDR, Long>, JpaSpecificationExecutor<CDR> {
    List<CDR> findByDstIn(Iterable<String> dst);

    List<CDR> findByCallDateBetween(Timestamp start, Timestamp end);

    List<CDR> findByDst(String dst);

    List<CDR> findBySrc(String src);
}
