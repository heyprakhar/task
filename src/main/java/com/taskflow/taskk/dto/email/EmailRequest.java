package com.taskflow.taskk.dto.email;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EmailRequest {

    private List<String> to;

    private List<String> cc;

    private List<String> bcc;

    private String replyTo;

    private String subject;

    private String body;

    @Builder.Default
    private boolean html = true;

    private List<EmailAttachment> attachments;
}