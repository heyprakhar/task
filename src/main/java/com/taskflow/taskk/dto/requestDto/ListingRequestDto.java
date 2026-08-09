package com.taskflow.taskk.dto.requestDto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ListingRequestDto {

    @Size(max=50, message = "Search term must not exceed {max} characters")
    private String search;

    @Builder.Default
    @Min(value = 1, message = "Page number must be greater than or equal to {value}")
    private Integer page = 1;

    @Builder.Default
    @Min(value = 1, message = "Size must be greater than or equal to {value}")
    @Max(value = 100, message = "Size must not exceed {value}")
    private Integer size = 10;

    @Size(max=100, message = "Sort field must not exceed {max} characters")
    private String sortBy;

    @Builder.Default
    @Pattern(regexp="(?i)(asc|desc)$", message = "Sort order must be either 'asc' or 'desc' (case insensitive)")
    private String sortOrder = "desc";

    private Long loggedInUserId;

    private String fromDate;

    private String toDate;

    private String direction;

}
