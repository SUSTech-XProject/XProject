package com.ooad.xproject.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FileConfig {

    @Value("${ooad.file.path.upload-root}")
    private String uploadRoot;

    @Value("${ooad.file.path.download-root}")
    private String downloadRoot;

    @Value("${ooad.file.path.output-root}")
    private String outputRoot;

    @Value("${ooad.file.path.input-root}")
    private String inputRoot;

    @Value("${ooad.file.path.resource-root}")
    private String resourceRoot;
}
