package tech.romashov.dale.application.web.retails;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RetailsRepository extends CrudRepository<RetailEntity, Integer> {
    List<RetailEntity> findByVendor(String vendor);
}
