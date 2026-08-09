package com.taskflow.taskk.mapper;

import com.taskflow.taskk.dto.email.EmailRequest;
import com.taskflow.taskk.dto.email.EmailTemplateRequest;

public class EmailMapper {

    private EmailMapper() {
    }

    public static EmailRequest toEmailRequest(EmailTemplateRequest request, String renderedBody) {

        if (request == null) {
            return null;
        }

        return EmailRequest.builder()
                .to(request.getTo())
                .cc(request.getCc())
                .bcc(request.getBcc())
                .replyTo(request.getReplyTo())
                .subject(request.getSubject())
                .body(renderedBody)
                .html(true)
                .attachments(request.getAttachments())
                .build();
    }
}