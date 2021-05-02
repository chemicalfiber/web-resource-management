package cn.cf.webresourcemanagement.controller;

import cn.cf.webresourcemanagement.eneity.DownloadInfo;
import cn.cf.webresourcemanagement.repository.DownloadInfoRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class DownloadController {
    @Autowired
    private DownloadInfoRepository downloadInfoRepository;
    // 向文件下载页面跳转
    @GetMapping("/toDownload")
    public String toDownload(Model model){
//        你需要提前将提供下载的文件放到你的桌面下的「downloadFile」文件夹下
        String dirPath = getDirPath();
        File f = new File(dirPath);
        if (!f.exists()) {
            System.out.println(dirPath + "不存在！");
        }
        File[] fa = f.listFiles();
        // 创建存放可下载文件名称的列表
        ArrayList fileList = new ArrayList();
        // 遍历并添加需要的文件到可下载列表
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + " （是目录，不支持下载）");
            } else {
                System.out.println("添加文件：" + fs.getName());
                // TODO：识别不必要的文件系统系统/操作系统标示文件，防止其被错误地添加到下载列表中，例如：「.DS_Store」(MacOS Spotlight索引文件)
                fileList.add(fs.getName());
            }
        }
        model.addAttribute("fileList",fileList);
        return "download";
    }
    // 文件下载管理
    @GetMapping("/download")
    public ResponseEntity<byte[]> fileDownload(String filename, HttpServletRequest request) throws IOException {    // 此处参数中的「filename」必须与「download.html」中提交的参数相同，不能有大小写差别
        System.out.println("要下载的文件名是：" + filename);
        // 指定要下载的文件的根路径
//        你需要提前将提供下载的文件放到你的桌面下的「downloadFile」文件夹下
        String dirPath = getDirPath();
        System.out.println("文件将会从这个地址开始下载：" + dirPath);
        // 创建文件对象
        File file = new File(dirPath + File.separator + filename);
        // 设置响应头
        HttpHeaders httpHeaders = new HttpHeaders();
        // 通知浏览器将返回的数据以下载形式打开
        filename = getFilename(request,filename);
        httpHeaders.setContentDispositionFormData("attachment",filename);
        // 定义以流的形式下载返回文件数据
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 将下载日志写入数据库
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setName(file.getName());   // 并不直接返回filename，因为此时的filename是响应头中的乱码
        downloadInfo.setDownloadtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        downloadInfo.setDownloadip(request.getRemoteAddr());
        downloadInfoRepository.save(downloadInfo);
        try{
            // 此处的「FileUtils」类属于「org.apache.commons.io.FileUtils」包
            return new ResponseEntity<>(FileUtils.readFileToByteArray(file),httpHeaders, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage().getBytes(),httpHeaders, HttpStatus.OK);
        }
    }
    // 中文编码处理
    private String getFilename(HttpServletRequest request,String filename) throws UnsupportedEncodingException {    // 此处参数中的「filename」必须与「download.html」中提交的参数相同，不能有大小写差别
        String[] IEBrowserKeyWords = {"MSIE","Trident","EDGE"};
        // 获取请求头代理信息
        String userAgent = request.getHeader("User-Agent");
        for (String keyWord : IEBrowserKeyWords){
            if (userAgent.contains(keyWord)){
                // IE浏览器统一为UTF-8编码
                return URLEncoder.encode(filename,"UTF-8").replace("+"," ");
            }
        }
        // 其他浏览器直接返回ISO-8859-1编码
        return new String(filename.getBytes("UTF-8"),"ISO-8859-1");
    }
    // 获取操作系统类型，并自动拼接提供下载文件的文件夹路径
    public String getDirPath(){
        String dirPath;
        // 获取运行当前程序的操作系统名称
        String systemName = System.getProperty("os.name");
        // 判断操作系统类型
        if (systemName.contains("Windows")){
            // Windows下，用户名是「%username%」,可以根据用户名拼接用户路径
            // 用户路径是「%userprofile%」，在Java中使用该环境变量时，无需声明百分号
            System.out.println("当前操作系统的用户目录是：" + System.getenv("userprofile"));
            // Windows用户请检查你的注册表！如果你的桌面路径被手动设置过，或者使用盗版GHOST操作系统，「桌面」路径大概率不在「%userprofile%\Desktop」路径下
            // Windows「桌面」路径的精确查找方法：查看注册表的「HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer\User Shell Folders」下的「Desktop」值
            // 这可能会导致「java.io.fileNotFoundException」
            dirPath = System.getenv("userprofile") + "/Desktop/downloadFile/";
        }else{
            // 在Mac或Linux中，用户名是「$USER」,可以根据用户名拼接用户路径
            // 用户目录是「$HOME」，这里直接使用「$HOME」
            // 请注意！如果你的Linux系统语言不是英语，「桌面」路径大概率不是「$HOME/Desktop」，检查你的系统语言设置
            // 这可能会导致「java.io.fileNotFoundException」
            System.out.println("当前操作系统的用户目录是：" + System.getenv("HOME"));
            dirPath = System.getenv("HOME") + "/Desktop/downloadFile/";
        }
        return dirPath;
    }
}
