package com.bwie.hm0422.vo;

import com.bwie.hm0422.model.TBorrow;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <FileName> BorrowVo
 * <Author>   lqz
 * <Time>  2020/4/23 19:50
 * <Desc> 实体类
 **/
@Data
public class BorrowVo{
    private String id;

    private String bookId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date borrowingTime;

    private String userId;

    private String status;
    private String userName;

    private String bookName;
}
