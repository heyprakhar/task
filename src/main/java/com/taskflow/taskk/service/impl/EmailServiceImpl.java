package com.taskflow.taskk.service.impl;

import com.taskflow.taskk.config.email.EmailProperties;
import com.taskflow.taskk.dto.email.EmailAttachment;
import com.taskflow.taskk.dto.email.EmailRequest;
import com.taskflow.taskk.dto.email.EmailTemplateRequest;
import com.taskflow.taskk.exceptions.EmailException;
import com.taskflow.taskk.mapper.EmailMapper;
import com.taskflow.taskk.service.serviceInterface.EmailService;
import com.taskflow.taskk.service.serviceInterface.EmailTemplateService;
import com.taskflow.taskk.service.serviceInterface.EmailValidationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailTemplateService emailTemplateService;
    private final EmailProperties emailProperties;
    private final EmailValidationService emailValidationService;

    @Override
    public void send(EmailRequest request) {

        emailValidationService.validateEmailRequest(request);

        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            configureRecipients(mimeMessageHelper, request);
            mimeMessageHelper.setFrom(emailProperties.getFrom());

            if (StringUtils.hasText(request.getReplyTo())) {
                mimeMessageHelper.setReplyTo(request.getReplyTo());
            }

            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.setText(request.getBody(), request.isHtml());
            addAttachments(mimeMessageHelper, request.getAttachments());
            mailSender.send(message);

            log.info("Email sent successfully. Recipient count: {}", request.getTo().size());

        } catch (MessagingException exception) {
            log.error("Failed to send email. Recipient count: {}", request.getTo().size(), exception);
            throw new EmailException("Failed to send email", exception);
        }

    }

    @Override
    public void sendTemplate(EmailTemplateRequest emailTemplateRequest) {

        emailValidationService.validateTemplateRequest(emailTemplateRequest);


        String renderedBody = emailTemplateService.render(
                emailTemplateRequest.getTemplate().getTemplateName(),
                emailTemplateRequest.getVariables()
        );

        EmailRequest emailRequest = EmailMapper.toEmailRequest(emailTemplateRequest, renderedBody);

        send(emailRequest);

    }

    private void configureRecipients(MimeMessageHelper helper, EmailRequest request) throws MessagingException {

        helper.setTo(request.getTo().toArray(new String[0]));

        if (request.getCc() != null && !request.getCc().isEmpty()) {
            helper.setCc(request.getCc().toArray(new String[0]));
        }

        if (request.getBcc() != null && !request.getBcc().isEmpty()) {
            helper.setBcc(request.getBcc().toArray(new String[0]));
        }

    }

    private void addAttachments(MimeMessageHelper helper, List<EmailAttachment> attachments) throws MessagingException {

        if (attachments == null || attachments.isEmpty()) {
            return;
        }

        for (EmailAttachment attachment : attachments) {
            if (attachment == null) {
                continue;
            }

            if (!StringUtils.hasText(attachment.getFileName())) {
                throw new EmailException("Attachment file name must not be blank");
            }

            if (attachment.getContent() == null) {
                throw new EmailException("Attachment content must not be null");
            }

            if (!StringUtils.hasText(attachment.getContentType())) {
                throw new EmailException("Attachment content type must not be blank");
            }

            helper.addAttachment(
                    attachment.getFileName(),
                    new ByteArrayResource(attachment.getContent()),
                    attachment.getContentType()
            );

        }
    }

}
