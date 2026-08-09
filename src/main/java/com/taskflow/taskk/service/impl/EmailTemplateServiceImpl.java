package com.taskflow.taskk.service.impl;

import com.taskflow.taskk.exceptions.EmailTemplateException;
import com.taskflow.taskk.service.serviceInterface.EmailTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Collections;
import java.util.Map;

import static com.taskflow.taskk.common.utils.Constants.EMAIL_TEMPLATE_PREFIX;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final TemplateEngine templateEngine;

    @Override
    public String render(String templateName, Map<String, Object> variables) {

        if (templateName == null || templateName.isBlank()) {
            throw new EmailTemplateException("Email template name must not be null or blank");
        }

        try {

            Context context = new Context();
            context.setVariables(variables != null ? variables : Collections.emptyMap());
            String templatePath = EMAIL_TEMPLATE_PREFIX + templateName;

            return templateEngine.process(templatePath, context);

        } catch (Exception exception) {

            log.warn("Failed to render email template: {}", templateName, exception);

            throw new EmailTemplateException("Failed to render email template: " + templateName, exception);

        }

    }
}
