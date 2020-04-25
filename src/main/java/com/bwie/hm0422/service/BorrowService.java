package com.bwie.hm0422.service;

import com.bwie.hm0422.dao.TBorrowMapper;
import com.bwie.hm0422.model.TBorrow;
import com.bwie.hm0422.model.TBorrowExample;
import com.bwie.hm0422.vo.BorrowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <FileName> BorrowService
 * <Author>   lqz
 * <Time>  2020/4/23 18:08
 * <Desc> 业务逻辑层
 **/
@Service
public class BorrowService {
    @Autowired
    TBorrowMapper tBorrowMapper;

    public List<BorrowVo> queryList(String bId) {

        return tBorrowMapper.selectByBid(bId);
    }
}
