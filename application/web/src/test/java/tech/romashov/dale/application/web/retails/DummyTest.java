package tech.romashov.dale.application.web.retails;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.romashov.dale.application.web.App;
import tech.romashov.dale.application.web.deploy.UnitTestsDatabaseConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class, UnitTestsDatabaseConfiguration.class})
public class DummyTest {
    @Autowired
    private RetailService retailService;

    @Test
    public void itWorks() {
        Iterable<RetailEntity> all = retailService.getAll();
    }
}
