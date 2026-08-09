package com.taskflow.taskk.dto.email;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailAttachment {

    private String fileName;

    private byte[] content;

    private String contentType;
}