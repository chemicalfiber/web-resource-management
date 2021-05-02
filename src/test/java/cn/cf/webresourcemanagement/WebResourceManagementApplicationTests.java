package cn.cf.webresourcemanagement;

import cn.cf.webresourcemanagement.eneity.FileInfo;
import cn.cf.webresourcemanagement.repository.FileInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class WebResourceManagementApplicationTests {
    @Autowired
    private FileInfoRepository fileInfoRepository;


    @Test
    void contextLoads() {
//        String path = "G:/lxz/20130611"; // 路径
        String path;
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
            path = System.getenv("userprofile") + "/Desktop/downloadFile/";
        }else{
            // 在Mac或Linux中，用户名是「$USER」,可以根据用户名拼接用户路径
            // 用户目录是「$HOME」，这里直接使用「$HOME」
            // 请注意！如果你的Linux系统语言不是英语，「桌面」路径大概率不是「$HOME/Desktop」，检查你的系统设置
            // 这将会导致「java.io.fileNotFoundException」
            System.out.println("当前操作系统的用户目录是：" + System.getenv("HOME"));
            path = System.getenv("HOME") + "/Desktop/downloadFile/";
        }
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return;
        }

        File[] fa = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + " [目录]");
            } else {
                System.out.println(fs.getName());
            }
        }
    }

}
