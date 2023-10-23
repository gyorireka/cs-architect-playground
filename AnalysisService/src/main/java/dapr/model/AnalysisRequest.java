package dapr.model;

import java.util.UUID;

public record AnalysisRequest(
    UUID id,
    String requestedByUser,
    String startDate,
    String endDate
) {
}
