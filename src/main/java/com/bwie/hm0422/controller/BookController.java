package com.bwie.hm0422.controller;

import com.bwie.hm0422.model.TBook;
import com.bwie.hm0422.model.TUser;
import com.bwie.hm0422.service.BookService;
import com.bwie.hm0422.utils.UploadFileUtils;
import com.bwie.hm0422.vo.BookVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <FileName> BookController
 * <Author>   lqz
 * <Time>  2020/4/22 20:09
 * <Desc> 控制层
 **/
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    RedisTemplate redisTemplate;

    @Value("${file.upload.path}")
    private String filePath ;

    @Value("${file.upload.relative}")
    private String relativePath;


    /**
     * 列表
     * @param tBook
     * @param currentPage
     * @return
     */
    @RequestMapping("/queryBookList")
    public ModelAndView queryBookList(TBook tBook, @RequestParam(defaultValue = "1") int currentPage) {
        ModelAndView modelAndView = new ModelAndView("list");
        PageHelper.startPage(currentPage,2);
        List<BookVo> books = bookService.queryBookList(tBook);
        PageInfo<BookVo> page = new PageInfo<>(books);
        modelAndView.addObject("list", page.getList());
        modelAndView.addObject("page",page);
        return modelAndView;
    }

    /**
     * 借还
     * @param tBook
     * @param session
     * @return
     */
    @RequestMapping("/borrowOrReturn")
    public String borrowOrReturn(TBook tBook, HttpSession session){
        TUser user = (TUser) session.getAttribute("user");
        bookService.borrowOrReturn(tBook,user);
        return "redirect:/book/queryBookList";

    }

    /**
     * 去添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "bk_add";
    }

    /**
     * 添加书籍
     * @param file
     * @param tBook
     * @param model
     * @return
     */
    @RequestMapping("/addBook")
    public String add(@RequestParam("file") MultipartFile file, TBook tBook, Model model){
        //对图片 进行上传并且赋值
        if (file.getSize()>0){
            String picUrl = UploadFileUtils.upload(file, filePath);
            tBook.setPicUrl(picUrl);
        }

        int flag = bookService.addBook(tBook);

        if (flag>0){
            //添加成功返回列表页面
            return "redirect:/book/queryBookList";
        }else{
            //添加失败返回添加
            model.addAttribute("msg","添加失败");
            return "bk_add";
        }
    }

    /**
     * 点赞方法
     * @param id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/click")
    @ResponseBody
    public String click(String id, HttpServletRequest request,Model model){

        TUser user = (TUser) request.getSession().getAttribute("user");
        if(user!=null){
            //判断当前用户是否点赞过
            Boolean aBoolean = redisTemplate.hasKey(user.getuId() + "click_" + id);
            if(!aBoolean){
                //完成点赞的功能
                redisTemplate.opsForValue().increment(user.getuId()+"click_"+id,1);
                return "yes";
            }else {
                return "no";
            }

        }else{
            return "no";
        }

    }

    @RequestMapping("/xq")
    @ResponseBody
    public BookVo queryById(String bId){
        redisTemplate.opsForValue().increment(bId,1);

        TBook tBook = new TBook();
        tBook.setbId(bId);
        BookVo bookVo = bookService.queryBookList(tBook).get(0);
        Integer num = (Integer) redisTemplate.opsForValue().get(bId);
        bookVo.setTraffic(new Short(String.valueOf(num)));
        return bookVo;
    }


}
