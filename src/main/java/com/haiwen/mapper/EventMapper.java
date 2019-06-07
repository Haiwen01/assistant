package com.haiwen.mapper;

import com.haiwen.entity.EventDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface EventMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(EventDO record);

    int insertSelective(EventDO record);

    EventDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EventDO record);

    int updateByPrimaryKey(EventDO record);

    List<EventDO> selectByStartTime(@Param("dateTime") Date dateTime);
}