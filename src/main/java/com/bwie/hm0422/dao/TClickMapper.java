package com.bwie.hm0422.dao;

import com.bwie.hm0422.model.TClick;
import com.bwie.hm0422.model.TClickExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TClickMapper {
    int countByExample(TClickExample example);

    int deleteByExample(TClickExample example);

    int insert(TClick record);

    int insertSelective(TClick record);

    List<TClick> selectByExample(TClickExample example);

    int updateByExampleSelective(@Param("record") TClick record, @Param("example") TClickExample example);

    int updateByExample(@Param("record") TClick record, @Param("example") TClickExample example);

    void updateByBookId(TClick t);
}