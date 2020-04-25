package com.bwie.hm0422.service;

import cn.hutool.core.util.RandomUtil;
import com.bwie.hm0422.dao.TBookMapper;
import com.bwie.hm0422.dao.TBorrowMapper;
import com.bwie.hm0422.dao.TClickMapper;
import com.bwie.hm0422.model.*;
import com.bwie.hm0422.vo.BookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <FileName> BookService
 * <Author>   lqz
 * <Time>  2020/4/22 20:06
 * <Desc> 业务逻辑层
 **/
@Service
public class BookService {
    @Autowired
    TBookMapper tBookMapper;
    @Autowired
    TBorrowMapper tBorrowMapper;
    @Autowired
    TClickMapper tClickMapper;

    public List<BookVo> queryBookList(TBook tBook) {
        return tBookMapper.selectBookVoList(tBook);
    }

    public String borrowOrReturn(TBook tBook, TUser tUser) {
        //判断归还还是借出
        String str = null;
        if(tBook.getBorrowingFlag()==0){
            //借出
            //1.修改状态,更改时间,增加浏览次数
            Date date = new Date();
            tBook.setBorrowingTime(date);
            tBook.setBorrowingCount((short) (tBook.getBorrowingCount()+1));
            tBook.setBorrowingFlag((short) 1);
            tBookMapper.updateByPrimaryKeySelective(tBook);
            //2.增加记录表一条记录
            TBorrow tBorrow = new TBorrow();
            tBorrow.setStatus("借出记录");
            tBorrow.setBookId(tBook.getbId());
            tBorrow.setBorrowingTime(date);
            tBorrow.setId(RandomUtil.randomUUID().replace("-",""));
            tBorrow.setUserId(tUser.getuId());
            tBorrowMapper.insert(tBorrow);
            str="0";
        }else {
            //归还
            //1.修改状态
            tBook.setBorrowingFlag((short) 0);
            tBookMapper.updateByPrimaryKeySelective(tBook);
            //2.增加记录表一条记录
            TBorrow tBorrow = new TBorrow();
            tBorrow.setStatus("归还记录");
            tBorrow.setBookId(tBook.getbId());
            tBorrow.setBorrowingTime(new Date());
            tBorrow.setId(RandomUtil.randomUUID().replace("-",""));
            tBorrow.setUserId(tUser.getuId());
            tBorrowMapper.insert(tBorrow);
            str="1";
        }
        return str;
    }

    @Transactional
    public int addBook(TBook tBook) {
        //给书籍生成数据
        String bId = UUID.randomUUID().toString().replace("-", "");
        tBook.setbId(bId);
        tBook.setBorrowingTime(new Date());
        tBook.setBorrowingFlag((short) 0);
        tBook.setBorrowingCount((short)0);
        //添加点击量表
        TClick tClick = new TClick();
        tClick.setbId(bId);
        tClick.setTraffic((short) 0);
        tClickMapper.insert(tClick);
        return tBookMapper.insert(tBook);
    }


}
