package com.project.ccts.repository;

import com.project.ccts.model.entities.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

    Node findByNodeIdentifier(String nodeIdentifier);

}
