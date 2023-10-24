package dapr.service;

import dapr.dto.AnalysisRequestInDto;
import dapr.model.AnalysisRequest;
import dapr.model.AnalysisResult;
import io.dapr.client.DaprClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static dapr.Constants.*;

@Service
public class AnalysisService {

  private static final Logger log = LoggerFactory.getLogger(AnalysisResult.class);
  private final DaprClient daprClient;

  @Autowired
  public AnalysisService(DaprClient daprClient) {
    this.daprClient = daprClient;
  }

  public void publishEvent(AnalysisRequestInDto data) {
    UUID randomId = UUID.randomUUID();

    AnalysisRequest analysisRequest = new AnalysisRequest(
        randomId,
        data.getRequestedByUser(),
        data.getStartDateTime(),
        data.getEndDateTime()
    );

    log.info("Publishing event: {}", ANALYSIS_REQUEST_TOPIC);
    log.info("Publishing analysis request: {}", analysisRequest);
    daprClient.publishEvent(PUBSUB, ANALYSIS_REQUEST_TOPIC, analysisRequest).block();
  }
}
