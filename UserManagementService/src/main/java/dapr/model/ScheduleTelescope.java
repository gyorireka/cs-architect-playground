package dapr.model;

import java.util.UUID;

public record ScheduleTelescope(
        UUID id,
        String requestedByUser,
        String startDateTime,
        int endDateTime,
        String lensType
) {
}
