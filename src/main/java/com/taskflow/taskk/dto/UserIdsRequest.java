package com.taskflow.taskk.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO : this same dto will be used in auth module to invalidate sessions of multiple users at once
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIdsRequest {
    List<Long> userIds;
}
