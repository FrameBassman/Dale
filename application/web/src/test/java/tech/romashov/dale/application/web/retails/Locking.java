package tech.romashov.dale.application.web.retails;

import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.romashov.dale.application.web.App;
import tech.romashov.dale.application.web.deploy.UnitTestsDatabaseConfiguration;
import tech.romashov.dale.application.web.properties.SystemPropertiesRepository;
import tech.romashov.dale.application.web.properties.SystemPropertyEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class Locking extends RetailsTests {
    @Before
    public void before() {
        SystemPropertyEntity limit = propsRepository.findByKey("limit");
        limit.value = "2";
        propsRepository.save(limit);
        retailsRepository.deleteAll();
    }

    @Test
    public void itWorks() throws Exception {
        // Arrange
        addDummyFreeRetail(Vendors.ALL, "1.1.1.1");

        // Act
        retailService.lock(Vendors.ALL);

        // Assert
        assertThat(retailsRepository.findAll(), new IsIterableWithSize<>(equalTo(1)));
    }

    private RetailEntity addDummyFreeRetail(String vendor, String inetAddress) {
        RetailEntity existent = new RetailEntity();
        existent.status = Statuses.free;
        existent.createdAt = LocalDateTime.ofInstant(Instant.now().minusSeconds(10), TimeZone.getDefault().toZoneId());
        existent.vendor = vendor;
        existent.ip = inetAddress;
        return retailsRepository.save(existent);
    }
}
