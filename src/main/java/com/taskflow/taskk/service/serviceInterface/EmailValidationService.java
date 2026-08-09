package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.email.EmailRequest;
import com.taskflow.taskk.dto.email.EmailTemplateRequest;

public interface EmailValidationService {

    void validateEmailRequest(EmailRequest request);
    void validateTemplateRequest(EmailTemplateRequest request);

}
