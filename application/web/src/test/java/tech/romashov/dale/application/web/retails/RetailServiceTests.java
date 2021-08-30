package tech.romashov.dale.application.web.retails;

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

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, UnitTestsDatabaseConfiguration.class})
public class RetailServiceTests {
    @Autowired
    private RetailService retailService;

    @Autowired
    private RetailsRepository retailsRepository;

    @Autowired
    private SystemPropertiesRepository propsRepository;

    @Before
    public void before() {
        SystemPropertyEntity limit = propsRepository.findByKey("limit");
        limit.value = "2";
        propsRepository.save(limit);
        retailsRepository.deleteAll();
    }

    @Test
    public void addNew_sizeLowThanLimit_shouldBeAdded() throws RetailException {
        // Arrange
        RetailEntity existent = new RetailEntity();
        existent.status = Statuses.busy;
        existent.createdAt = LocalDateTime.ofInstant(Instant.now().minusSeconds(10), TimeZone.getDefault().toZoneId());
        existent.vendor = Vendors.ALL;
        existent.ip = "1.1.1.1";
        retailsRepository.save(existent);

        // Act
        RetailEntity result = retailService.add(Vendors.ALL, "2.2.2.2");

        // Assert
        List<RetailEntity> findAll = StreamSupport.stream(retailsRepository.findAll().spliterator(), false).collect(Collectors.toList());
        assertThat(findAll, hasSize(2));
    }
}
