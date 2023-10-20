package dapr.model;

import java.util.UUID;

public record AnalysisResult(
        UUID uid,
        int numberOfStarsFound
) {
}
