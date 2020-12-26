package com.ooad.xproject.vo;

import com.ooad.xproject.entity.Project;
import lombok.Data;

@Data
public class ProjectVO {
    private Integer projId;

    private String projName;
    private String courseName;
    private String term;        // E.g. 202001, 202003
    private String description;

    private Boolean stdJoin;
    private Boolean tchJoin;

    private String topics;
    private String projSettings;

    public void copyToProjCreate(Project proj) {
        proj.setProjName(projName);
        proj.setCourseName(courseName);
        proj.setTerm(term);
        proj.setDescription(description);
    }

    public void copyToProjUpdate(Project proj) {
        proj.setProjId(projId);

        proj.setProjName(projName);
        proj.setCourseName(courseName);
        proj.setTerm(term);
        proj.setDescription(description);

        proj.setStdJoin(stdJoin);
        proj.setTchJoin(tchJoin);

        proj.setTopics(topics);
        proj.setProjSettings(projSettings);
    }
}

