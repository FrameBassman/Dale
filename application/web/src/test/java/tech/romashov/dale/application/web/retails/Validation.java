package tech.romashov.dale.application.web.retails;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Validation {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void validateVendor() throws Exception {
        expectedEx.expect(RetailException.class);
        expectedEx.expectMessage("Parameter vendor is null or empty");

        RetailService service = new RetailService();
        service.add("", "1.1.1.1");
    }

    @Test
    public void validateInetAddress() throws Exception {
        expectedEx.expect(RetailException.class);
        expectedEx.expectMessage("Parameter ip is null or empty");

        RetailService service = new RetailService();
        service.add(Vendors.ALL, "");
    }
}
