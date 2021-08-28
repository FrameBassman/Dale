package tech.romashov.dale.application.web.retails;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.romashov.dale.application.web.retails.ui.AddRetailView;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("retails")
public class RetailsController {
    @Autowired
    private RetailsRepository retails;
    @Autowired
    private RetailService retailService;
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

    @PostMapping("add")
    public RetailEntity add(@RequestBody AddRetailView view) throws Exception {
        return retailService.add(view.getVendor(), view.getIp());
    }
}
