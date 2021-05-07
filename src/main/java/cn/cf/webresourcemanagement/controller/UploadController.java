package cn.cf.webresourcemanagement.controller;

import cn.cf.webresourcemanagement.eneity.FileInfo;
import cn.cf.webresourcemanagement.repository.FileInfoRepository;
import cn.cf.webresourcemanagement.util.GetPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
@Controller
public class UploadController {
    @Autowired
    private FileInfoRepository fileInfoRepository;
    // 向文件上传页面跳转
    @GetMapping("/toUpload")
    public String toUpload(){
        return "upload";
    }
    // 文件上传管理
    @PostMapping("/uploadFile")
    public String uploadFile(MultipartFile[] fileUpload, Model model, HttpServletRequest request){
        // 默认上传成功，返回状态信息
        model.addAttribute("uploadStatus","上传成功！");
        // 指定文件上传目录，不存在就新建一个
        String dirPath = GetPath.getUploadPath();
        // 遍历上传的文件，并写入本地磁盘
        for (MultipartFile file : fileUpload){
            // 获取文件名和后缀名
            String fileName = file.getOriginalFilename();
            System.out.println("上传的文件原名为：" + fileName);
            // 获取文件后缀名
            String[] split = fileName.split("\\.");
            String fileType = split[split.length - 1];
            // 重新生成文件名(根据具体情况声明对应文件名)
            // 请注意，如果不生成「UUID+文件名」，则后来的重名文件会替换先前的文件
            fileName = UUID.randomUUID() + "_" + fileName;
            System.out.println("重新生成的文件名为：" + fileName);
            File filePath = new File(dirPath);
            if (!filePath.exists()){
                filePath.mkdirs();
            }
            System.out.println("=================");
            try{
                file.transferTo(new File(dirPath + fileName));
                // 文件上传成功后，将文件信息存到数据库
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFilename(fileName);
                fileInfo.setFiletype(fileType);
                fileInfo.setUploadtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                fileInfo.setUploadip(request.getRemoteAddr());
                fileInfoRepository.save(fileInfo);
            }catch (Exception e){
                e.printStackTrace();
                // 上传失败，返回失败信息
                model.addAttribute("uploadStatus","上传失败：" + e.getMessage());
            }
        }
        // 携带上传状态信息回调到文件上传页面
        return "upload";
    }
}
