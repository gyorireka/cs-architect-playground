package dapr.controller;

import dapr.model.AnalysisRequest;
import dapr.model.ImageAddress;
import dapr.service.AnalysisService;
import io.dapr.Topic;
import io.dapr.client.DaprClient;
import io.dapr.client.domain.CloudEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static dapr.Constants.*;

@RestController
public class ImageListController {

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ImageListController.class);

  private final AnalysisService analysisService;
  private final DaprClient daprClient;

  @Autowired
  public ImageListController(AnalysisService analysisService, DaprClient daprClient) {
    this.analysisService = analysisService;
    this.daprClient = daprClient;
  }

  @GetMapping(path = "/collectImageAddresses")
  public void triggerImageAddressSentBack() {
    /*List<ImageAddress> addresses = new ArrayList<>();
    addresses.add(new ImageAddress("1", "11"));
    addresses.add(new ImageAddress("2", "22"));
    addresses.add(new ImageAddress("3", "33"));
    addresses.add(new ImageAddress("4", "44"));
    log.info("Image addresses sent back:");*/
    List<String> addresses = new ArrayList<>();

    daprClient.publishEvent(PUBSUB, IMAGE_LIST_RESULT_TOPIC, addresses).block();
  }


  @PostMapping(path = "/collectImageAddresses")
  @Topic(name = IMAGE_LIST_RESULT_TOPIC, pubsubName = PUBSUB)
  public ResponseEntity<Void> getImageAddresses(@RequestBody final CloudEvent<List<String>> event) {
    log.info("Got image addresses: {}", event.getData());
    // TODO: Download images --- Needed?
    analysisService.analyseResultSend((ArrayList<String>) event.getData());
    return ResponseEntity.ok().build();
  }
}
