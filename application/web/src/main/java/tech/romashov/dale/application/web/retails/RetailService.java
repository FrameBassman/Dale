package tech.romashov.dale.application.web.retails;

import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetailService {
    @Autowired
    private RetailsRepository retails;
    private int limit = 5;

    public RetailEntity add(String vendor, String inetAddress) throws ArrayIndexOutOfBoundsException {
        if (isNullOrEmpty(vendor) || isNullOrEmpty(inetAddress)) {
            throw new ArrayIndexOutOfBoundsException("Parameter is null or empty");
        }

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

    public RetailEntity lock(String vendor) throws ArrayIndexOutOfBoundsException {
        if (isNullOrEmpty(vendor)) {
            throw new ArrayIndexOutOfBoundsException("Parameter is null or empty");
        }

        List<RetailEntity> free = retails.findByVendorOrderByCreatedAt(vendor)
                .stream()
                .filter(retail -> retail.status.equals(Statuses.free))
                .collect(Collectors.toList());
        if (free.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        RetailEntity candidate = free.get(0);
        candidate.status = Statuses.busy;
        return retails.save(candidate);
    }

    public RetailEntity addDummy() throws UnknownHostException {
        return addNew(Vendors.ALL, InetAddress.getByName("127.0.0.1").getHostAddress());
    }

    public Iterable<RetailEntity> getAll() {
        return retails.findAll();
    }

    private RetailEntity addNew(String vendor, String inetAddress) {
        RetailEntity newInstance = new RetailEntity();
        newInstance.vendor = vendor;
        newInstance.status = Statuses.free;
        newInstance.ip = inetAddress;
        newInstance.createdAt = LocalDateTime.now();
        return retails.save(newInstance);
    }

    private boolean isNullOrEmpty(String candidate) {
        return candidate == null || candidate.trim().equals("");
    }
}
