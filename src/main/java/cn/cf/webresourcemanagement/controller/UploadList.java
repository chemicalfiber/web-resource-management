package cn.cf.webresourcemanagement.controller;

import cn.cf.webresourcemanagement.eneity.DownloadInfo;
import cn.cf.webresourcemanagement.eneity.FileInfo;
import cn.cf.webresourcemanagement.repository.DownloadInfoRepository;
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
    @Autowired
    private DownloadInfoRepository downloadInfoRepository;

    @GetMapping("/list")
    public String toUploadList(Model model){
        List<FileInfo> uploadInfoList = fileInfoRepository.findAll();
        for (int i = 0;i < uploadInfoList.size();i++){
            String[] split = uploadInfoList.get(i).getFilename().split("_");
            uploadInfoList.get(i).setFilename(split[split.length-1]);   //将数据库中的信息取出，文件名使用下划线「_」分割，只取后面的文件名，舍弃UUID
        }
        List<DownloadInfo> downloadInfoList = downloadInfoRepository.findAll();
        model.addAttribute("uploadList",uploadInfoList);
        model.addAttribute("downloadList",downloadInfoList);
        return "list";
    }
}
