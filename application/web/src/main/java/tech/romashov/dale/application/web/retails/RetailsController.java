package tech.romashov.dale.application.web.retails;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("retails")
public class RetailsController {
    @Autowired
    private RetailsRepository retails;
    private Logger logger;

    @GetMapping("all")
    public Iterable<RetailEntity> GetAll() {
        return retails.findAll();
    }

    @GetMapping("create")
    public RetailEntity Create() throws UnknownHostException {
        RetailEntity retail = new RetailEntity();
        retail.setIp(InetAddress.getByName("127.0.0.1").getHostAddress());
        retail.setStatus(Statuses.free);
        retail.setVendor(Vendors.ALL);
        return retails.save(retail);
    }
}
