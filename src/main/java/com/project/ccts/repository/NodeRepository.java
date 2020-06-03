package com.project.ccts.repository;

import com.project.ccts.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

    Node findByUniqueIdentifier(String uniqueIdentifier);

}
