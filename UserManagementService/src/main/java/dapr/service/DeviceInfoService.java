package dapr.service;

import dapr.model.DeviceInfoRequest;
import io.dapr.client.DaprClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static dapr.Constants.DEVICE_INFO_TOPIC;
import static dapr.Constants.PUBSUB;

@Service
public class DeviceInfoService {

  private static final Logger log = LoggerFactory.getLogger(DeviceInfoService.class);

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
    log.info("Publishing event: {}", DEVICE_INFO_TOPIC);
    log.info("Publishing deviceInfoRequest: {}", deviceInfoRequest);

    daprClient.publishEvent(PUBSUB, DEVICE_INFO_TOPIC, deviceInfoRequest).block();
  }
}
