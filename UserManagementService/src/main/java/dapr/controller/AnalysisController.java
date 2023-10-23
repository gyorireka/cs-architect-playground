package dapr.controller;

import dapr.dto.AnalysisRequestInDto;
import dapr.model.AnalysisResult;
import dapr.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;

import static dapr.Constants.*;

@RestController
public class AnalysisController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AnalysisController.class);

    private final AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping(path="/requestAnalysis")
    public void requestAnalysis(@RequestBody final AnalysisRequestInDto analysisRequest) {
        log.info("Analysis request event published!");
        analysisService.publishEvent(analysisRequest);
    }

    @PostMapping(path = "/collectAnalysis")
    @Topic(name = ANALYSIS_RESULT_TOPIC, pubsubName = PUBSUB)
    public ResponseEntity<Void> collectAnalysis(@RequestBody final CloudEvent<AnalysisResult> event) {
    	log.info("Got analysis result: {}", event.getData());
      return ResponseEntity.ok().build();
    }
}
