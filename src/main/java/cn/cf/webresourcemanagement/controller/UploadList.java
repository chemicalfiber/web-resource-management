package cn.cf.webresourcemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UploadList {
    @GetMapping("/List")
    public String toUploadList(){
        // TODO：将数据库中的信息取出，文件名使用下划线「_」分割，只取最后面的
        return "uploadList";
    }
}
