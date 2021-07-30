package tech.romashov.dale.application.web.retails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.Inet4Address;

@Entity
@Table(name = "retails")
public class RetailEntity {
    @Id private Integer id;
    private Inet4Address ip;
    private String vendor;
    private String status;

    public RetailEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Inet4Address getIp() {
        return ip;
    }

    public void setIp(Inet4Address ip) {
        this.ip = ip;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
