package tech.romashov.dale.application.web.retails;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.romashov.dale.application.web.App;
import tech.romashov.dale.application.web.deploy.UnitTestsDatabaseConfiguration;
import tech.romashov.dale.application.web.properties.SystemPropertiesRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, UnitTestsDatabaseConfiguration.class})
public abstract class RetailsTests {
    @Autowired
    protected RetailService retailService;

    @Autowired
    protected RetailsRepository retailsRepository;

    @Autowired
    protected SystemPropertiesRepository propsRepository;

    protected RetailEntity addDummyFreeRetail(String inetAddress) {
        RetailEntity existent = new RetailEntity();
        existent.status = Statuses.free;
        existent.createdAt = LocalDateTime.ofInstant(Instant.now().minusSeconds(10), TimeZone.getDefault().toZoneId());
        existent.vendor = Vendors.ALL;
        existent.ip = inetAddress;
        return retailsRepository.save(existent);
    }

    protected RetailEntity addDummyBusyRetail(String inetAddress) {
        RetailEntity existent = new RetailEntity();
        existent.status = Statuses.busy;
        existent.createdAt = LocalDateTime.ofInstant(Instant.now().minusSeconds(10), TimeZone.getDefault().toZoneId());
        existent.vendor = Vendors.ALL;
        existent.ip = inetAddress;
        return retailsRepository.save(existent);
    }
}
