package com.taskflow.taskk.dto.email;

import com.taskflow.taskk.enums.EmailTemplate;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class EmailTemplateRequest {

    private List<String> to;

    private List<String> cc;

    private List<String> bcc;

    private String replyTo;

    private String subject;

    private EmailTemplate template;

    private Map<String, Object> variables;

    private List<EmailAttachment> attachments;
}