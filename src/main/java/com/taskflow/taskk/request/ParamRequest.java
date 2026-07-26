package com.taskflow.taskk.request;

import com.taskflow.taskk.dto.requestDto.ListingRequestDto;
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
    private Boolean activeUser;
    private Long userRoleId;
    private String search;

    public boolean isRecords() { return Boolean.TRUE.equals(records); }

}
