package tech.romashov.dale.application.web.retails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RetailService {
    @Autowired
    private RetailsRepository retails;
    private int limit = 5;

    public RetailEntity add(String vendor, String inetAddress) throws ArrayIndexOutOfBoundsException {
//        ArrayList<RetailEntity> all = retails.findByVendorOrderedByCreationDate(vendor);
//        ArrayList<RetailEntity> free = all.stream()
//                .filter(retail -> retail.getStatus().equals(Statuses.free))
//                .collect(Collectors.toCollection(ArrayList::new));
//        if (all.size() > limit) {
//            if (free.isEmpty()) {
//                throw new ArrayIndexOutOfBoundsException();
//            }
//
//            RetailEntity old = free.get(free.size() - 1);
//            retails.delete(old);
//            return addNew(vendor, inetAddress);
//        }

        return addNew(vendor, inetAddress);
    }

    private RetailEntity addNew(String vendor, String inetAddress) {
        RetailEntity newInstance = new RetailEntity();
        newInstance.setVendor(vendor);
        newInstance.setStatus(Statuses.free);
        newInstance.setIp(inetAddress);
        newInstance.setCreatedAt(LocalDateTime.now());
        return retails.save(newInstance);
    }
}
