package com.project.ccts.api;

import com.project.ccts.dto.CustomResponseObjectDTO;
import com.project.ccts.dto.projectStatistics.NodeLocationMarker;
import com.project.ccts.model.entities.Node;
import com.project.ccts.service.NodeService;
import com.project.ccts.service.ProjectStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

import static com.project.ccts.dto.CustomResponseObjectUtil.createResponse;

@RestController
@RequestMapping("/api/statistics/project")
public class ProjectStatisticsApi {

    private ProjectStatisticsService projectStatisticsService;
    private NodeService nodeService;

    @Autowired
    public void setProjectStatisticsService(ProjectStatisticsService projectStatisticsService) {
        this.projectStatisticsService = projectStatisticsService;
    }
    @Autowired
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping(path = "all/{time}",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getProjectStatistics(@PathVariable(value = "time") Integer time) {
         if (time < 2) {
            time = 2;
        }
        return new ResponseEntity<>(createResponse(HttpStatus.OK, projectStatisticsService.getStatistics(PageRequest.of(0,time))),HttpStatus.OK);
    }
    @GetMapping(path = "location/nodes",produces = "application/json")
    public ResponseEntity<CustomResponseObjectDTO> getNodeLocation(){
        Collection<Node> nodes = nodeService.findAll();
        if (nodes != null){
            Collection<NodeLocationMarker> nodeLocationMarkers = new ArrayList<>();
            nodes.stream().forEach(node -> nodeLocationMarkers.add(new NodeLocationMarker(node.getLocality().getGpsLocation(),node.getLocality().getName())));
            return new ResponseEntity<>(createResponse(HttpStatus.OK,nodeLocationMarkers),HttpStatus.OK);
        }
        return new ResponseEntity<>(createResponse(HttpStatus.ACCEPTED,"No nodes found"),HttpStatus.ACCEPTED);
    }
}
