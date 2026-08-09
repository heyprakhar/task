package com.taskflow.taskk.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResponseDTO<T> {

    private Long total;
    private Map<String, Long> entityTypeCounts;
    private Map<String, Long> entityStateCounts;
    private Map<String, Object> groupedEntityCounts;
    private Map<String, Object> additionalMetadata;
    private List<T> records;

}
