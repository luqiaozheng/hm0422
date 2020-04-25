package com.bwie.hm0422.controller;

import com.bwie.hm0422.model.TBook;
import com.bwie.hm0422.model.TBorrow;
import com.bwie.hm0422.service.BookService;
import com.bwie.hm0422.service.BorrowService;
import com.bwie.hm0422.vo.BorrowVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <FileName> BorrowController
 * <Author>   lqz
 * <Time>  2020/4/23 18:07
 * <Desc> 控制层
 **/
@Controller
@RequestMapping("/borrow")
public class BorrowController {
    @Autowired
    BorrowService borrowService;

    @RequestMapping("/putName")
    public ModelAndView putName(TBook tBook, HttpSession session){
        session.setAttribute("bookName",tBook.getBookName());
        return queryList(tBook,1);
    }

    /**
     * 查询书籍信息,书籍表和点击率表两表联查
     * @param tBook
     * @param currentPage
     * @return
     */
    @RequestMapping("/queryList")
    public ModelAndView queryList(TBook tBook, @RequestParam(defaultValue = "1")Integer currentPage){
        ModelAndView modelAndView = new ModelAndView("bo_list");
        PageHelper.startPage(currentPage,3);
        List<BorrowVo> tBorrows = borrowService.queryList(tBook.getbId());
        PageInfo<BorrowVo> pageInfo = new PageInfo<>(tBorrows);
        modelAndView.addObject("list",pageInfo.getList());
        modelAndView.addObject("page",pageInfo);
        modelAndView.addObject("bId",tBook.getbId());
        return modelAndView;
    }

    /**
     * 三表联查将结果返回页面
     * @param id
     * @return
     */
    @RequestMapping("/queryBorrowList")
    @ResponseBody
    public List<BorrowVo> queryBorrowList(String id){
        List<BorrowVo> tBorrows = borrowService.queryList(id);
        return tBorrows;
    }
}
