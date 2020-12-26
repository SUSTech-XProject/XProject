package com.ooad.xproject.vo;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UploadFileProjIdVO {
    private MultipartFile[] files;
    private Integer projId;
}
