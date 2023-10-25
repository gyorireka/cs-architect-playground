package dapr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisRequestInDto {

  @NotNull
  private String requestedByUser;

  @NotNull
  private String startDateTime;

  @NotNull
  private String endDateTime;
}
