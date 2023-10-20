package dapr.model;

import java.util.UUID;

public record DeviceInfoRequest(
        UUID uid,
        String requestedByUser,
        String dateTime
) {
}
