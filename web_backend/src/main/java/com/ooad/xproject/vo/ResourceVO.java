package com.ooad.xproject.vo;

import com.ooad.xproject.entity.Resource;
import com.ooad.xproject.entity.Teacher;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceVO {
    private Resource resource;
    private Teacher creator;
}
