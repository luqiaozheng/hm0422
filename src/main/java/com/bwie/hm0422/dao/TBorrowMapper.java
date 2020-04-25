package com.bwie.hm0422.dao;

import com.bwie.hm0422.model.TBorrow;
import com.bwie.hm0422.model.TBorrowExample;
import java.util.List;

import com.bwie.hm0422.vo.BorrowVo;
import org.apache.ibatis.annotations.Param;

public interface TBorrowMapper {
    int countByExample(TBorrowExample example);

    int deleteByExample(TBorrowExample example);

    int deleteByPrimaryKey(String id);

    int insert(TBorrow record);

    int insertSelective(TBorrow record);

    List<TBorrow> selectByExample(TBorrowExample example);

    TBorrow selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TBorrow record, @Param("example") TBorrowExample example);

    int updateByExample(@Param("record") TBorrow record, @Param("example") TBorrowExample example);

    int updateByPrimaryKeySelective(TBorrow record);

    int updateByPrimaryKey(TBorrow record);

    List<BorrowVo> selectByBid(String bId);
}