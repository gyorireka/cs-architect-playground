package dapr.model;

import java.util.UUID;

public record ScheduleTelescope(
        UUID id,
        String requestedByUser,
        String startDate,
        String endDate,
        String lensType
) {
}
