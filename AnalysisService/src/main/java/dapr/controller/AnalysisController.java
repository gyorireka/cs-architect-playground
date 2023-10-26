package dapr.controller;

import dapr.model.AnalysisRequest;
import dapr.model.ImageAddress;
import dapr.service.AnalysisService;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static dapr.Constants.*;

@RestController
public class AnalysisController {
    private static final Logger log = LoggerFactory.getLogger(AnalysisController.class);

    private final AnalysisService analysisService;

    @Autowired
    public AnalysisController(final AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping(path = "/testResult")
    public ResponseEntity<Void> analyseResult() {
        sendingAnalyseResult(new ArrayList<>());
        return ResponseEntity.accepted().build();
    }

    @PostMapping(path = "/requestAnalysis")
    @Topic(name = ANALYSIS_REQUEST_TOPIC, pubsubName = PUBSUB)
    private ResponseEntity<Void> requestAnalysis(@RequestBody final CloudEvent<AnalysisRequest> event) {
        log.info("Analysis requested: {}", event.getData());
        analysisService.askForImageAddresses(event.getData());
        return ResponseEntity.ok().build();
    }
    private void sendingAnalyseResult(ArrayList<String> addresses) {
        log.info("Sending analysis result");

        analysisService.analyseResultSend(addresses);
    }
}
