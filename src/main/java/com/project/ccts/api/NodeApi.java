package com.project.ccts.api;

import com.project.ccts.service.VisitService;
import com.project.ccts.util.protocol.VisitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/node")
public class NodeApi {

    private VisitService visitService;

    @Autowired
    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping("/addVisits")
    @PreAuthorize("hasAnyAuthority('NODE_WRITE_PRIVILEGE', 'NODE_READ_PRIVILEGE')")
    public ResponseEntity<VisitTemplate> addVisits(@RequestBody VisitTemplate body) {
        //TODO: finish this
        visitService.addVisits(body);
        return new ResponseEntity<VisitTemplate>(body, HttpStatus.OK);
    }
}
