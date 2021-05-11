package cn.cf.webresourcemanagement.entity;

import javax.persistence.*;

@Entity(name = "filedownload")
public class DownloadInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String downloadip;
    private String downloadtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadip() {
        return downloadip;
    }

    public void setDownloadip(String downloadip) {
        this.downloadip = downloadip;
    }

    public String getDownloadtime() {
        return downloadtime;
    }

    public void setDownloadtime(String downloadtime) {
        this.downloadtime = downloadtime;
    }

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", downloadip='" + downloadip + '\'' +
                ", downloadtime='" + downloadtime + '\'' +
                '}';
    }
}
