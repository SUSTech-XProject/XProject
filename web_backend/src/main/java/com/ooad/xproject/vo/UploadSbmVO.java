package com.ooad.xproject.vo;

import com.ooad.xproject.entity.Record;
import com.ooad.xproject.entity.Teacher;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UploadSbmVO {
    private MultipartFile[] files;
    private Integer sbmId;
}
