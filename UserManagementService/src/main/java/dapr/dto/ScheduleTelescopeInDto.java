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
public class ScheduleTelescopeInDto {

    @Null
    private String id;

    @NotNull
    private String requestedByUser;

    @NotNull
    private String startDateTime;

    @NotNull
    private int endDateTime;

    @NotNull
    private String lensType;
}
