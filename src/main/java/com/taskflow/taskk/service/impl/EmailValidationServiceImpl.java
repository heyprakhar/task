package com.taskflow.taskk.service.impl;

import com.taskflow.taskk.dto.email.EmailRequest;
import com.taskflow.taskk.dto.email.EmailTemplateRequest;
import com.taskflow.taskk.exceptions.EmailException;
import com.taskflow.taskk.service.serviceInterface.EmailValidationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmailValidationServiceImpl implements EmailValidationService {

    @Override
    public void validateEmailRequest(EmailRequest request) {

        if (request == null) {
            throw new EmailException("Email request must not be null");
        }

        if (request.getTo() == null || request.getTo().isEmpty()) {
            throw new EmailException("At least one email recipient is required");
        }

        if (!StringUtils.hasText(request.getSubject())) {
            throw new EmailException("Email subject must not be blank");
        }

        if (request.getBody() == null) {
            throw new EmailException("Email body must not be null");
        }

    }

    @Override
    public void validateTemplateRequest(EmailTemplateRequest request) {

        if (request == null) {
            throw new EmailException("Email template request must not be null");
        }

        if (request.getTo() == null || request.getTo().isEmpty()) {
            throw new EmailException("At least one email recipient is required");
        }

        if (!StringUtils.hasText(request.getSubject())) {
            throw new EmailException("Email subject must not be blank");
        }

        if (request.getTemplate() == null) {
            throw new EmailException("Email template must not be null");
        }

    }
}
