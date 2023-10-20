package dapr.service;

import dapr.model.DeviceInfoRequest;
import io.dapr.client.DaprClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class DeviceInfoService {

  private static final Logger log = LoggerFactory.getLogger(DeviceInfoService.class);
  private static final String TOPIC_DEVICEINFO = "deviceInfo";
  private static final String PUBSUB = "pubsub";

  private final DaprClient daprClient;

  @Autowired
  public DeviceInfoService(DaprClient daprClient) {
    this.daprClient = daprClient;
  }

  public void publishEvent() {
    DeviceInfoRequest deviceInfoRequest = new DeviceInfoRequest(
            UUID.randomUUID(),
            "testUser",
            new Date().toString()
    );
    log.info("Publishing event: {}", TOPIC_DEVICEINFO);
    log.info("Publishing deviceInfoRequest: {}", deviceInfoRequest);

    daprClient.publishEvent(PUBSUB, TOPIC_DEVICEINFO, deviceInfoRequest).block();
  }
}
