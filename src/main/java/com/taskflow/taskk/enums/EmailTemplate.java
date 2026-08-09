package com.taskflow.taskk.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTemplate {

    WELCOME("welcome"),
    USER_UPDATED("user-updated"),
    USER_ACTIVATED("user-activated"),
    USER_DEACTIVATED("user-deactivated"),
    PASSWORD_RESET("password-reset"),
    TASK_ASSIGNED("task-assigned"),
    TASK_STATUS_UPDATED("task-status-updated");

    private final String templateName;
}