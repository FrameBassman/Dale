package tech.romashov.dale.application.web.retails;

import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Before;
import org.junit.Test;
import tech.romashov.dale.application.web.properties.SystemPropertyEntity;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class Adding extends RetailsTests {
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
        addDummyBusyRetail("1.1.1.1");

        // Act
        Map<String, RetailEntity> result = retailService.add(Vendors.ALL, "2.2.2.2");

        // Assert
        assertThat(retailsRepository.findAll(), new IsIterableWithSize<>(equalTo(2)));
        assertThat(retailsRepository.findByIp("1.1.1.1"), not(nullValue()));
        assertThat(retailsRepository.findByIp("2.2.2.2"), not(nullValue()));
        assertThat(result.size(), equalTo(1));
        assertThat(result.get("new").ip, equalTo("2.2.2.2"));
    }

    @Test(expected = RetailException.class)
    public void addNew_allExistentShouldBeBusy_shouldNotBeAdded() throws RetailException {
        // Arrange
        addDummyBusyRetail("1.1.1.1");
        addDummyBusyRetail("2.2.2.2");

        // Act
        retailService.add(Vendors.ALL, "3.3.3.3");
    }

    @Test
    public void addNew_oneOfExistentShouldBeFree_shouldBeAdded() throws RetailException {
        // Arrange
        RetailEntity busy = addDummyBusyRetail("1.1.1.1");
        RetailEntity free = addDummyFreeRetail("2.2.2.2");

        // Act
        Map<String, RetailEntity> result = retailService.add(Vendors.ALL, "3.3.3.3");

        // Assert
        assertThat(retailsRepository.findAll(), iterableWithSize(2));
        assertThat(retailsRepository.findByIp("1.1.1.1"), not(empty()));
        assertThat(retailsRepository.findByIp("2.2.2.2"), empty());
        assertThat(retailsRepository.findByIp("3.3.3.3"), not(empty()));
        assertThat(result.size(), equalTo(2));
        assertThat(result.get("new").ip, equalTo("3.3.3.3"));
        assertThat(result.get("old").ip, equalTo("2.2.2.2"));
    }
}
