package dapr.controller;

import dapr.dto.ScheduleTelescopeInDto;
import dapr.service.ScheduleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

    private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping(path = "/schedule")
    public ResponseEntity<Void> schedule(@RequestBody @Valid ScheduleTelescopeInDto scheduleParam) {
        log.info("ScheduleController.schedule() called");

        scheduleService.publishEvent(scheduleParam);

        return ResponseEntity.accepted().build();
    }
}
