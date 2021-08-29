package tech.romashov.dale.application.web.properties;

import org.springframework.data.repository.CrudRepository;

public interface SystemPropertiesRepository extends CrudRepository<SystemPropertyEntity, Integer> {
    SystemPropertyEntity findByKey(String key);
}
