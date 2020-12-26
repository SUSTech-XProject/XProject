package com.ooad.xproject.service;

import com.ooad.xproject.dto.AnnDTO;
import com.ooad.xproject.entity.Announcement;

import java.util.List;

public interface AnnService {
    List<AnnDTO> getAnnList(int projId);

    boolean updateAnn(Announcement announcement);

    boolean addAnn(Announcement announcement);

    boolean deleteAnn(int annId);

    Announcement getAnnByAnnId(Integer annId);
}
