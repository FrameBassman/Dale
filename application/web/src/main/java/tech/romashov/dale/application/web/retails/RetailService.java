package tech.romashov.dale.application.web.retails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class RetailService {
    @Autowired
    private RetailsRepository retails;
    private int limit = 5;

    public RetailEntity add(String vendor, String inetAddress) throws ArrayIndexOutOfBoundsException {
        ArrayList<RetailEntity> all = retails.findByVendorOrderByCreatedAt(vendor);
        ArrayList<RetailEntity> free = all.stream()
                .filter(retail -> retail.status.equals(Statuses.free))
                .collect(Collectors.toCollection(ArrayList::new));
        if (all.size() >= limit) {
            if (free.isEmpty()) {
                throw new ArrayIndexOutOfBoundsException();
            }

            RetailEntity old = free.get(0);
            retails.delete(old);
            return addNew(vendor, inetAddress);
        }

        return addNew(vendor, inetAddress);
    }

    public RetailEntity addDummy() throws UnknownHostException {
        return addNew(Vendors.ALL, InetAddress.getByName("127.0.0.1").getHostAddress());
    }

    private RetailEntity addNew(String vendor, String inetAddress) {
        RetailEntity newInstance = new RetailEntity();
        newInstance.vendor = vendor;
        newInstance.status = Statuses.free;
        newInstance.ip = inetAddress;
        newInstance.createdAt = LocalDateTime.now();
        return retails.save(newInstance);
    }
}
