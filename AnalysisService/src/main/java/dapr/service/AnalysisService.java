package dapr.service;

import dapr.analyis.AnalyseResultClient;
import dapr.model.AnalysisRequest;
import dapr.model.ImageAddress;
import io.dapr.client.DaprClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static dapr.Constants.*;

@Service
public class AnalysisService {

  private static final Logger log = LoggerFactory.getLogger(AnalysisService.class);

  private final AnalyseResultClient analyseResultClient;
  private final DaprClient daprClient;

  @Autowired
  public AnalysisService(AnalyseResultClient analyseResultClient, DaprClient daprClient) {
    this.analyseResultClient = analyseResultClient;
    this.daprClient = daprClient;
  }

  public void analyseResultSend(ArrayList<String> addresses) {
    analyseResultClient.analyseResultSend(addresses);
  }

  public void askForImageAddresses(AnalysisRequest analysisRequest) {
    log.info("Images requested for analysis!");
    daprClient.publishEvent(PUBSUB, IMAGE_LIST_REQUEST_TOPIC, analysisRequest).block();
  }
}
