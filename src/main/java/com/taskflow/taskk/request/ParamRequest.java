package com.taskflow.taskk.request;

import com.taskflow.taskk.dto.requestDto.ListingRequestDto;
import com.taskflow.taskk.enums.TaskActivityActorType;
import com.taskflow.taskk.enums.TaskActivityType;
import com.taskflow.taskk.enums.TaskLinkType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ParamRequest extends ListingRequestDto{

    private List<String> entityState;
    private List<String> entityType;
    private Boolean records = Boolean.FALSE;
    private Boolean active;
    private Long userRoleId;
    private String search;
    private boolean activateUser;
    private TaskLinkType taskLinkType;
    private Long sourceTaskId;
    private Long targetTaskId;
    private Long taskId;
    private TaskActivityType taskActivityType;
    private Long taskActivityPerformedBy;
    private TaskActivityActorType taskActivityActorType;

    public boolean isRecords() { return Boolean.TRUE.equals(records); }

}
