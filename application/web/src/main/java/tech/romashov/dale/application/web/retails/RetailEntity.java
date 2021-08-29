package tech.romashov.dale.application.web.retails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "retails")
public class RetailEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    public Integer id;

    @Column(name = "ip")
    public String ip;

    @Column(name = "vendor")
    public String vendor;

    @Column(name = "status")
    public String status;

    @Column(name = "created_at")
    public LocalDateTime createdAt;
}
