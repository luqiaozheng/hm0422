package com.bwie.hm0422.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @描述：
 * @作者：zhangyuyang
 * @日期：2020/3/28 10:36
 */
public class UploadFileUtils {

    /**
     * 图片上传
     * @param file 上传的文件
     * @param filePath 文件地址
     * @return
     */
    public static  String  upload(MultipartFile file , String filePath){
        //获取上传文件的名
        String filename = file.getOriginalFilename();
        //定义上传图片保存路径
        String path =  filePath +"rotPhoto/";
        //如果不存在则新建上传文件路径
        File filepath = new File(path, filename);
        if(!filepath.getParentFile().exists()){
            filepath.getParentFile().mkdirs();
        }
        //写入文件到指定目录   File.separator == /
        try {
            file.transferTo(new File(path+File.separator+filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将保存的文件的地址响应给html页面
        return   "/images/rotPhoto/"+filename ;

    }




}
