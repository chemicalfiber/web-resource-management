package cn.cf.webresourcemanagement.controller;

import cn.cf.webresourcemanagement.entity.DownloadInfo;
import cn.cf.webresourcemanagement.repository.DownloadInfoRepository;
import cn.cf.webresourcemanagement.util.GetPath;
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
        String dirPath = GetPath.getDownloadPath();
        File f = new File(dirPath);
        if (!f.exists()) {
            System.out.println(dirPath + "不存在！");
        }
        File[] fa = f.listFiles();
        // 创建存放可下载文件名称的列表
        ArrayList fileList = new ArrayList();
        // 遍历并添加需要的文件到可下载列表
        for (File fs : fa) {
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + " （是目录，不支持下载）");
            } else if (fs.getName().startsWith("._") || fs.getName().equals(".DS_Store")) {
                System.out.println(fs.getName() + " （是无效文件，不添加到可下载列表）");
            } else {
                System.out.println("添加文件：" + fs.getName());
                fileList.add(fs.getName());
            }
        }
        model.addAttribute("fileList",fileList);
        System.out.println("=================");
        return "download";
    }
    // 文件下载管理
    @GetMapping("/download")
    public ResponseEntity<byte[]> fileDownload(String filename, HttpServletRequest request) throws IOException {    // 此处参数中的「filename」必须与「download.html」中提交的参数相同，不能有大小写差别
        System.out.println("要下载的文件名是：" + filename);
        // 指定要下载的文件的根路径
//        你需要提前将提供下载的文件放到你的桌面下的「downloadFile」文件夹下
        String dirPath;
        // 如果是从上传日志页面进行文件下载，则从前端传回来的filename参数会带有UUID，UUID和文件名的分隔符是在第36号索引上的下划线「_」
        // 如果是从上传日志页面进行文件下载，就拼接上传文件夹uploadFile的路径
        char[] chars = filename.toCharArray();
        if (chars.length>36&&chars[36]=='_'){
            dirPath = GetPath.getUploadPath();
        // 如果是从下载文件页面进行的普通下载，就拼接下载提供文件夹downloadFile的路径
        }else{
            dirPath = GetPath.getDownloadPath();
        }
        System.out.println("文件将会从这个路径开始下载：" + dirPath);
        // 创建文件对象
        File file = new File(dirPath + File.separator + filename);
        // 设置响应头
        HttpHeaders httpHeaders = new HttpHeaders();
        // 通知浏览器将返回的数据以下载形式打开
        // 如果是从上传日志页面进行下载，先将UUID处理掉，避免客户端下载的文件带有无意义的UUID
        if (chars.length>36&&chars[36]=='_'){
            filename = getFilename(request,(filename.substring(37)));  // 如果从36号索引开始截取，会包含下划线，故从37号索引开始截取
        }else {
            filename = getFilename(request,filename);
        }
        httpHeaders.setContentDispositionFormData("attachment",filename);
        // 定义以流的形式下载返回文件数据
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 将下载日志写入数据库
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setName(file.getName());   // 并不直接使用filename，因为此时的filename是响应头中的乱码
        downloadInfo.setDownloadtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        downloadInfo.setDownloadip(request.getRemoteAddr());
        downloadInfoRepository.save(downloadInfo);
        System.out.println("=================");
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
                // 微软系浏览器统一为UTF-8编码
                // TODO：chromium内核的Edge可能无需处理，但是这段代码可能会将chromium内核的Edge也包含在内，解决它
                return URLEncoder.encode(filename,"UTF-8").replace("+"," ");
            }
        }
        // 其他浏览器直接返回ISO-8859-1编码
        return new String(filename.getBytes("UTF-8"),"ISO-8859-1");
    }
}
