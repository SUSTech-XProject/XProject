package com.ooad.xproject.service.impl;

import com.ooad.xproject.dto.AnnDTO;
import com.ooad.xproject.entity.Announcement;
import com.ooad.xproject.mapper.AnnouncementMapper;
import com.ooad.xproject.service.AnnService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnServiceImpl implements AnnService {

    private final AnnouncementMapper announcementMapper;

    public AnnServiceImpl(AnnouncementMapper announcementMapper) {
        this.announcementMapper = announcementMapper;
    }

    @Override
    public List<AnnDTO> getAnnList(int projId) {
        return announcementMapper.selectAnnDTOByProjId(projId);
    }

    @Override
    public boolean updateAnn(Announcement announcement) {
        int affectedRowCnt = announcementMapper.updateByPrimaryKeySelective(announcement);
        return affectedRowCnt == 1;
    }

    @Override
    public boolean addAnn(Announcement announcement) {
        int affectedRowCnt = announcementMapper.insertSelective(announcement);
        return affectedRowCnt == 1;
    }

    @Override
    public boolean deleteAnn(int annId) {
        int affectedRowCnt = announcementMapper.deleteByPrimaryKey(annId);
        return affectedRowCnt == 1;
    }

    @Override
    public Announcement getAnnByAnnId(Integer annId) {
        return announcementMapper.selectByPrimaryKey(annId);
    }
}
