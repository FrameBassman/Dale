package tech.romashov.dale.application.web.retails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RetailService {
    @Autowired
    private RetailsRepository retails;
    private int limit = 5;

    public RetailEntity add(String vendor, String ip) throws Exception {
        ArrayList<RetailEntity> vendorRetails = new ArrayList<>(retails.findByVendor(vendor));
        if (vendorRetails.size() > limit) {
            throw new ArrayIndexOutOfBoundsException("");
        }
        return null;
    }
}
