package tech.romashov.dale.application.web.retails;

import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Before;
import org.junit.Test;
import tech.romashov.dale.application.web.properties.SystemPropertyEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Locking extends RetailsTests {
    @Before
    public void before() {
        SystemPropertyEntity limit = propsRepository.findByKey("limit");
        limit.value = "2";
        propsRepository.save(limit);
        retailsRepository.deleteAll();
    }

    @Test
    public void oneFreeRetailExists_tryLockIt_shouldBeLocked() throws Exception {
        // Arrange
        addDummyFreeRetail(Vendors.ALL, "1.1.1.1");

        // Act
        retailService.lock(Vendors.ALL);

        // Assert
        assertThat(retailsRepository.findAll(), new IsIterableWithSize<>(equalTo(1)));
        assertThat(retailsRepository.findByIp("1.1.1.1").status, equalTo(Statuses.busy));
    }

    @Test
    public void oneBusyRetailExists_tryLockIt_shouldNotBeLocked() {
        // Arrange
        addDummyBusyRetail("1.1.1.1");

        // Act
        try {
            retailService.lock(Vendors.ALL);
            assertThat("Exception should be thrown", false);
        } catch (RetailException exception) {

            // Assert
            assertThat(exception.getMessage(), equalTo("There are no free retails here"));
        }
        assertThat(retailsRepository.findAll(), new IsIterableWithSize<>(equalTo(1)));
        assertThat(retailsRepository.findByIp("1.1.1.1").status, equalTo(Statuses.busy));
    }

    @Test
    public void oneFreeForNCRVendor_oneBusyForAllVendor_tryLockForNCR_shouldBeLocked() throws Exception {
        addDummyBusyRetail(Vendors.ALL, "1.1.1.1");
        addDummyFreeRetail(Vendors.NCR, "2.2.2.2");

        retailService.lock(Vendors.NCR);

        assertThat(retailsRepository.findAll(), new IsIterableWithSize<>(equalTo(2)));
        assertThat(retailsRepository.findByIp("2.2.2.2").status, equalTo(Statuses.busy));
    }

    private RetailEntity addDummyFreeRetail(String vendor, String inetAddress) {
        RetailEntity existent = new RetailEntity();
        existent.status = Statuses.free;
        existent.createdAt = LocalDateTime.ofInstant(Instant.now().minusSeconds(10), TimeZone.getDefault().toZoneId());
        existent.vendor = vendor;
        existent.ip = inetAddress;
        return retailsRepository.save(existent);
    }

    private RetailEntity addDummyBusyRetail(String vendor, String inetAddress) {
        RetailEntity existent = new RetailEntity();
        existent.status = Statuses.busy;
        existent.createdAt = LocalDateTime.ofInstant(Instant.now().minusSeconds(10), TimeZone.getDefault().toZoneId());
        existent.vendor = vendor;
        existent.ip = inetAddress;
        return retailsRepository.save(existent);
    }
}
