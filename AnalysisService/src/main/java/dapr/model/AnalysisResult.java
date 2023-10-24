package dapr.model;

import java.util.UUID;

public record AnalysisResult(
        UUID uid,
        String imageAddress,
        int numberOfStars,
        int numberOfBlackHoles
) {
}
