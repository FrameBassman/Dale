package tech.romashov.dale.application.web.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemPropertiesService {
    @Autowired
    private SystemPropertiesRepository properties;

    public int getLimit() {
        return Integer.parseInt(properties.findByKey("limit").value);
    }
}
