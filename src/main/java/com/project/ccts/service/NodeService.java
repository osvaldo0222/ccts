package com.project.ccts.service;

import com.project.ccts.model.entities.Node;
import com.project.ccts.repository.NodeRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NodeService extends AbstractCrud<Node, Long> {

    private NodeRepository nodeRepository;

    @Autowired
    public void setNodeRepository(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public JpaRepository<Node, Long> getDao() {
        return nodeRepository;
    }

}
