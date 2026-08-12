package com.taskflow.taskk.enums;

public enum TaskActivityType {

    // Task lifecycle
    TASK_CREATED,
    TASK_DELETED,

    // Assignment
    TASK_ASSIGNED,
    TASK_UNASSIGNED,

    // Status / priority
    STATUS_CHANGED,
    PRIORITY_CHANGED,

    // Task information
    TITLE_CHANGED,
    DESCRIPTION_CHANGED,
    DUE_DATE_CHANGED,
    TASK_TYPE_CHANGED,

    // Task links
    TASK_LINK_CREATED,
    TASK_LINK_DELETED,

    // Comments
    COMMENT_ADDED,
    COMMENT_UPDATED,
    COMMENT_DELETED,

    // Other
    TASK_REOPENED,
    TASK_COMPLETED
}