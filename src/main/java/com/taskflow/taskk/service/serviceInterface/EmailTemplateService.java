package com.taskflow.taskk.service.serviceInterface;

import java.util.Map;

public interface EmailTemplateService {

        String render(String templateName, Map<String, Object> variables);

    }

