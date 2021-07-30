package tech.romashov.dale.application.web.retails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("retails")
public class RetailsController {
    @Autowired
    private RetailsRepository retails;
    private Logger logger;

    public RetailsController() {
        logger = LoggerFactory.getLogger(RetailsController.class);
        logger.info("Initialize RetailsController");
    }

    @GetMapping("all")
    public Iterable<RetailEntity> GetAll() {
        return retails.findAll();
    }
}
