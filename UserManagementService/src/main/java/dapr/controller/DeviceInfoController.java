package dapr.controller;

import dapr.service.DeviceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceInfoController {

  private static final Logger log = LoggerFactory.getLogger(DeviceInfoController.class);

  private final DeviceInfoService deviceInfoService;

  @Autowired
  public DeviceInfoController(DeviceInfoService deviceInfoService) {
    this.deviceInfoService = deviceInfoService;
  }

  @GetMapping(path = "/deviceInfo")
  public ResponseEntity<Void> deviceInfo() {
    log.info("deviceInfo requested");

    deviceInfoService.publishEvent();

    return ResponseEntity.accepted().build();
  }
}
