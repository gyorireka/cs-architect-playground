package dapr.service;

import dapr.dto.ScheduleTelescopeInDto;
import dapr.model.ScheduleTelescope;
import io.dapr.client.DaprClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static dapr.Constants.PUBSUB;
import static dapr.Constants.SCHEDULE_TOPIC;

@Service
public class ScheduleService {

    private static final Logger log = LoggerFactory.getLogger(ScheduleService.class);
    private final DaprClient daprClient;

    @Autowired
    public ScheduleService(DaprClient daprClient) {
        this.daprClient = daprClient;
    }

    public void publishEvent(ScheduleTelescopeInDto data) {
        UUID randomId = UUID.randomUUID();

        ScheduleTelescope scheduleTelescope = new ScheduleTelescope(
                randomId,
                data.getRequestedByUser(),
                data.getStartDateTime(),
                data.getEndDateTime(),
                data.getLensType()
        );

        log.info("Publishing event: {}", SCHEDULE_TOPIC);
        log.info("Publishing scheduleTelescope: {}", scheduleTelescope);
        daprClient.publishEvent(PUBSUB, SCHEDULE_TOPIC, scheduleTelescope).block();
    }
}
