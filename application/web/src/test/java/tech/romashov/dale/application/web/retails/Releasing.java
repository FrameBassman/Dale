package tech.romashov.dale.application.web.retails;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.iterableWithSize;

public class Releasing extends RetailsTests {
    @Before
    public void before() {
        retailsRepository.deleteAll();
    }

    @Test
    public void releaseBusyRetail_statusShouldBeChangedToFree() throws Exception {
        // Arrange
        addDummyBusyRetail("1.1.1.1");

        // Act
        Iterable<RetailEntity> result = retailService.release("1.1.1.1");

        // Assert
        assertThat(result, iterableWithSize(1));
        RetailEntity retail = result.iterator().next();
        assertThat(retail.ip, equalTo("1.1.1.1"));
        assertThat(retail.status, equalTo(Statuses.free));
        assertThat(retailsRepository.findByIp("1.1.1.1"), hasSize(1));
        assertThat(retailsRepository.findByIp("1.1.1.1").get(0).status, equalTo(Statuses.free));
    }
}
