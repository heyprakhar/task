package com.taskflow.taskk.service.serviceInterface;

import com.taskflow.taskk.dto.email.EmailRequest;
import com.taskflow.taskk.dto.email.EmailTemplateRequest;

public interface EmailService {

    void send(EmailRequest request);

    void sendTemplate(EmailTemplateRequest request);
}