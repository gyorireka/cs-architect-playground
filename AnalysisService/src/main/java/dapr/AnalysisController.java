package dapr;

import dapr.analyis.AnalyseResultClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalysisController {
    private static final Logger log = LoggerFactory.getLogger(AnalysisController.class);

    private final AnalyseResultClient analyseResultClient;

    public AnalysisController(final AnalyseResultClient analyseResultClient) {
        this.analyseResultClient = analyseResultClient;
    }

    @PostMapping(path = "/exitcam")
    public ResponseEntity<Void> analyseResult() {
        sendingAnalyseResult();
        return ResponseEntity.accepted().build();
    }

    private void sendingAnalyseResult() {
        log.info("Sending analysis result");

        analyseResultClient.analyseResultSend();
    }
}
