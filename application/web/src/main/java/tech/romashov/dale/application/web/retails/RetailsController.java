package tech.romashov.dale.application.web.retails;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.romashov.dale.application.web.retails.ui.requests.AddRetailView;
import tech.romashov.dale.application.web.retails.ui.requests.LockRetailView;
import tech.romashov.dale.application.web.retails.ui.requests.ReleaseRetailView;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("retails")
public class RetailsController {
    @Autowired
    private RetailsRepository retails;
    @Autowired
    private RetailService retailService;
    private Logger logger;

    @GetMapping("all")
    public Iterable<RetailEntity> all() {
        return retailService.getAll();
    }

    @GetMapping("create")
    public RetailEntity create() throws UnknownHostException {
        return retailService.addDummy();
    }

    @PostMapping("add")
    public Map<String, RetailEntity> add(@RequestBody AddRetailView view) throws Exception {
        return retailService.add(view.getVendor(), view.getIp());
    }

    @PostMapping("lock")
    public RetailEntity lock(@RequestBody LockRetailView view) throws Exception {
        return retailService.lock(view.getVendor());
    }

    @PostMapping("release")
    public RetailEntity release(@RequestBody ReleaseRetailView view) throws Exception {
        return retailService.release(view.getIp());
    }
}
