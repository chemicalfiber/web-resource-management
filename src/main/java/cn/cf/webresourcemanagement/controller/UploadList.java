package cn.cf.webresourcemanagement.controller;

import cn.cf.webresourcemanagement.eneity.FileInfo;
import cn.cf.webresourcemanagement.repository.FileInfoRepository;
import cn.cf.webresourcemanagement.util.GetPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.List;

@Controller
public class UploadList {
    @Autowired
    private FileInfoRepository fileInfoRepository;

    @GetMapping("/list")
    public String toUploadList(Model model){
        model.addAttribute("uploadList",fileInfoRepository.findAll());
        return "list";
    }

    @GetMapping("/deleteFile")
    public String deleteFile(String filename,Model model){
        String filePath = GetPath.getUploadPath();
        File file = new File(filePath + File.separator + filename);
        System.out.println("要删除的文件是：" + filename);
        file.delete();
        fileInfoRepository.delete(fileInfoRepository.findByFilename(filename));
        model.addAttribute("deleteStatus","删除成功！");
        model.addAttribute("uploadList",fileInfoRepository.findAll());
        return "/list";
    }
}
