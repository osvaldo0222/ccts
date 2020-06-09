package com.project.ccts.service;

import com.project.ccts.model.Node;
import com.project.ccts.repository.NodeRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.enums.NodeStatus;
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

    public Node findByNodeIdentifier(String uniqueIdentifier) {
        return nodeRepository.findByNodeIdentifier(uniqueIdentifier);
    }

    /**
     * Keep the node active when is function and have no visits to submit.
     *
     * @param nodeIdentifier
     * @throws Exception
     */
    public void keepalive(String nodeIdentifier) throws Exception {
        //Findig the node in the db
        Node node = findByNodeIdentifier(nodeIdentifier);

        if (node == null) {
            throw new Exception(String.format("Node with identifer %s not found", nodeIdentifier));
        }

        //Set the status to active
        node.getNodeCredential().setStatus(NodeStatus.ACTIVE);

        //updating the node
        createOrUpdate(node);
    }
}
