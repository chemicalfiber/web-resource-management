package cn.cf.webresourcemanagement.controller;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class GetPath {
    @GetMapping("/dir")
    public String getDir() {
        /*File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            // 啥都不干
        }
        if (path == null || !path.exists()) {
            path = new File("");
        }
        String pathStr = path.getAbsolutePath();
        return pathStr;

         */
        System.out.println("user.dir" + System.getProperty("user.dir"));
        try {
            String s = ResourceUtils.getURL("classpath:").getPath();
            System.out.println(s);
            return s;
        } catch (FileNotFoundException e) {
            return "error";
        }
    }
}