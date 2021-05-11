package cn.cf.webresourcemanagement.controller;

import cn.cf.webresourcemanagement.repository.DownloadInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DownloadList {
    @Autowired
    private DownloadInfoRepository downloadInfoRepository;
    @GetMapping("/downloadlist")
    public String toDownloadList(Model model){
        model.addAttribute("downloadList",downloadInfoRepository.findAll());
        return "downloadList";
    }
}
