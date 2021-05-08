package cn.cf.webresourcemanagement.repository;

import cn.cf.webresourcemanagement.eneity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo,Integer> {
    public FileInfo findByFilename(String filename);
}
