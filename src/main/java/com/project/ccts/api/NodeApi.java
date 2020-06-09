package com.project.ccts.api;

import com.project.ccts.dto.CustomResponseObjectDTO;
import com.project.ccts.service.NodeService;
import com.project.ccts.service.VisitService;
import com.project.ccts.dto.NodeSubmitVisitDTO;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.project.ccts.dto.CustomResponseObjectUtil.createResponse;

@RestController
@RequestMapping("/api/node")
@PreAuthorize("hasAnyAuthority('NODE_WRITE_PRIVILEGE', 'NODE_READ_PRIVILEGE')")
public class NodeApi {

    private VisitService visitService;
    private NodeService nodeService;

    @Value("${application.node.tagWaitTimeMilliseconds}")
    private Integer tagWaitTime;

    @Value("${application.node.keepaliveTimeMilliseconds}")
    private Integer keepaliveTime;

    @Autowired
    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    @Autowired
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping("/config")
    public ResponseEntity<CustomResponseObjectDTO> config() {
        Map<String, Object> config = new HashMap<>();
        config.put("tagWaitTime", tagWaitTime);
        config.put("keepaliveTime", keepaliveTime);
        return new ResponseEntity<>(createResponse(HttpStatus.OK, config), HttpStatus.OK);
    }

    @PostMapping("/addVisits")
    public ResponseEntity<CustomResponseObjectDTO> addVisits(@RequestBody NodeSubmitVisitDTO visits) {
        try {
            visitService.addVisits(visits);
            return new ResponseEntity<>(createResponse(HttpStatus.OK, "Registered visits"), HttpStatus.OK);
        } catch (Exception e) {
            Logger.getInstance().getLog(getClass()).warn(e.getMessage());
            return new ResponseEntity<>(createResponse(HttpStatus.UNAUTHORIZED, e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/keepalive")
    public ResponseEntity<CustomResponseObjectDTO> keepalive(@RequestParam(name = "nodeIdentifier", required = true) String nodeIdentifier) {
        try {
            nodeService.keepalive(nodeIdentifier);
            return new ResponseEntity<>(createResponse(HttpStatus.OK, "Status updated"), HttpStatus.OK);
        } catch (Exception e) {
            Logger.getInstance().getLog(getClass()).warn(e.getMessage());
            return new ResponseEntity<>(createResponse(HttpStatus.UNAUTHORIZED, e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}
