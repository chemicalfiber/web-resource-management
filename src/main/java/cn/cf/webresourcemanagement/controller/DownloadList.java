package cn.cf.webresourcemanagement.controller;

import cn.cf.webresourcemanagement.eneity.DownloadInfo;
import cn.cf.webresourcemanagement.repository.DownloadInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DownloadList {
    @Autowired
    private DownloadInfoRepository downloadInfoRepository;
    @GetMapping("/downloadlist")
    public String toDownloadList(Model model){
        List<DownloadInfo> downloadInfoList = downloadInfoRepository.findAll();
        model.addAttribute("downloadList",downloadInfoList);
        return "downloadList";
    }
}
