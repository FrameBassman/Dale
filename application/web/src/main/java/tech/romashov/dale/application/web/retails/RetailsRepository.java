package tech.romashov.dale.application.web.retails;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface RetailsRepository extends CrudRepository<RetailEntity, Integer> {
//    ArrayList<RetailEntity> findByVendorOrderedByCreationDate(String vendor);
    List<RetailEntity> findByVendor(String vendor);
}
