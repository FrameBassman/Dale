package tech.romashov.dale.application.web.retails;

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

    public RetailEntity add(String vendor, String inetAddress) throws RetailException {
        validate("vendor", vendor);
        validate("ip", inetAddress);

        ArrayList<RetailEntity> all = retails.findByVendorOrderByCreatedAt(vendor);
        ArrayList<RetailEntity> free = all.stream()
                .filter(retail -> retail.status.equals(Statuses.free))
                .collect(Collectors.toCollection(ArrayList::new));
        if (all.size() >= limit) {
            if (free.isEmpty()) {
                throw new RetailException("There are no free retails for updating");
            }

            RetailEntity old = free.get(0);
            retails.delete(old);
            return addNew(vendor, inetAddress);
        }

        return addNew(vendor, inetAddress);
    }

    public RetailEntity lock(String vendor) throws RetailException {
        validate("vendor", vendor);

        List<RetailEntity> free = retails.findByVendorOrderByCreatedAt(vendor)
                .stream()
                .filter(retail -> retail.status.equals(Statuses.free))
                .collect(Collectors.toList());
        if (free.isEmpty()) {
            throw new RetailException("There are no free retails here");
        }
        RetailEntity candidate = free.get(0);
        candidate.status = Statuses.busy;
        return retails.save(candidate);
    }

    public RetailEntity release(String inetAddress) throws RetailException {
        validate("ip", inetAddress);
        RetailEntity toRelease = retails.findByIp(inetAddress);
        if (toRelease == null) {
            throw new RetailException(String.format("There is no retails with ip: %s", inetAddress));
        }
        toRelease.status = Statuses.free;
        return retails.save(toRelease);
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

    private void validate(String name, String value) throws RetailException {
        if (isNullOrEmpty(value)) {
            throw new RetailException(String.format("Parameter %s is null or empty", name));
        }
    }

    private boolean isNullOrEmpty(String candidate) {
        return candidate == null || candidate.trim().equals("");
    }
}
