package tech.romashov.dale.application.web.retails;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RetailsRepository extends CrudRepository<RetailEntity, Integer> {
    ArrayList<RetailEntity> findByVendorOrderByCreatedAt(String vendor);
    RetailEntity findByIp(String ip);
}
