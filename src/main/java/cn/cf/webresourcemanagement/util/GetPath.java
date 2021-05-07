package cn.cf.webresourcemanagement.util;

public class GetPath {
    // 获取操作系统类型，并自动拼接提供下载文件的文件夹路径
    public static String getDownloadPath(){
        String dirPath;
        // 获取运行当前程序的操作系统名称
        String systemName = System.getProperty("os.name");
        // 判断操作系统类型
        if (systemName.contains("Windows")){
            // Windows下，用户名是「%username%」,可以根据用户名拼接用户路径
            // 用户路径是「%userprofile%」，在Java中使用该环境变量时，无需声明百分号
            System.out.println("当前操作系统的用户目录是：" + System.getenv("userprofile"));
            /*
            * Windows用户请检查你的注册表！如果你的桌面路径被手动设置过，或者使用盗版GHOST操作系统，「桌面」路径大概率不在「%userprofile%\Desktop」路径下
            * Windows「桌面」路径的精确查找方法：查看注册表的「HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer\User Shell Folders」下的「Desktop」值
            * 这可能会导致「java.io.fileNotFoundException」
            */
            dirPath = System.getenv("userprofile") + "/Desktop/downloadFile/";
        }else{
            /*
            * 在Mac或Linux中，用户名是「$USER」,可以根据用户名拼接用户路径
            * 用户目录是「$HOME」，这里直接使用「$HOME」
            * 请注意！如果你的Linux系统语言不是英语，「桌面」路径大概率不是「$HOME/Desktop」，检查你的系统语言设置
            * 这可能会导致「java.io.fileNotFoundException」
            */
            System.out.println("当前操作系统的用户目录是：" + System.getenv("HOME"));
            dirPath = System.getenv("HOME") + "/Desktop/downloadFile/";
        }
        return dirPath;
    }
    // 获取操作系统类型，并自动拼接上传路径
    public static String getUploadPath(){
        String dirPath;
        String systemName = System.getProperty("os.name");
        if (systemName.contains("Windows")){
            System.out.println("当前操作系统的用户目录是：" + System.getenv("userprofile"));
            dirPath = System.getenv("userprofile") + "/Desktop/uploadFile/";
        }else{
            System.out.println("当前操作系统的用户目录是：" + System.getenv("HOME"));
            dirPath = System.getenv("HOME") + "/Desktop/uploadFile/";
        }
        System.out.println("上传文件保存的路径是：" + dirPath);
        return dirPath;
    }
}
