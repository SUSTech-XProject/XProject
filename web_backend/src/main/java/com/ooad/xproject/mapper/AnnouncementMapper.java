package com.ooad.xproject.mapper;

import com.ooad.xproject.dto.AnnDTO;
import com.ooad.xproject.entity.Announcement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementMapper {
    int deleteByPrimaryKey(Integer annId);

    int insert(Announcement record);

    int insertSelective(Announcement record);

    Announcement selectByPrimaryKey(Integer annId);

    int updateByPrimaryKeySelective(Announcement record);

    int updateByPrimaryKey(Announcement record);

    // extra

    List<Announcement> selectByProjId(Integer projId);

    List<AnnDTO> selectAnnDTOByProjId(int projId);
}