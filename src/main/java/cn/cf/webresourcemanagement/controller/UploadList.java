package cn.cf.webresourcemanagement.controller;

import cn.cf.webresourcemanagement.eneity.FileInfo;
import cn.cf.webresourcemanagement.repository.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UploadList {
    @Autowired
    private FileInfoRepository fileInfoRepository;

    @GetMapping("/list")
    public String toUploadList(Model model){
        List<FileInfo> uploadInfoList = fileInfoRepository.findAll();
        model.addAttribute("uploadList",uploadInfoList);
        return "list";
    }
}
