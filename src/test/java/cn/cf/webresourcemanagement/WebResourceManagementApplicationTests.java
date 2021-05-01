package cn.cf.webresourcemanagement;

import cn.cf.webresourcemanagement.eneity.FileInfo;
import cn.cf.webresourcemanagement.repository.FileInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;

@SpringBootTest
class WebResourceManagementApplicationTests {
    @Autowired
    private FileInfoRepository fileInfoRepository;


    @Test
    void contextLoads() {
        List<FileInfo> all = fileInfoRepository.findAll();
        Iterator<FileInfo> iterator = all.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}
