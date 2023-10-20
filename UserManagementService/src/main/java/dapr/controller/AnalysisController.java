package dapr.controller;

import dapr.model.AnalysisResult;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;

@RestController
public class AnalysisController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AnalysisController.class);

    @PostMapping(path = "/collectAnalysis")
    @Topic(name = "analysis", pubsubName = "pubsub")
    public ResponseEntity<Void> registerViolation(@RequestBody final CloudEvent<AnalysisResult> event) {
    	log.info("Got analysis result: {}", event.getData());
        return ResponseEntity.ok().build();
    }
    
}
