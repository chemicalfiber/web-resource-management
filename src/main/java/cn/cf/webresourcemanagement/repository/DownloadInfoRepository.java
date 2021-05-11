package cn.cf.webresourcemanagement.repository;

import cn.cf.webresourcemanagement.entity.DownloadInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadInfoRepository extends JpaRepository<DownloadInfo,Integer> {
}
