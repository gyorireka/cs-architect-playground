package dapr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

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
