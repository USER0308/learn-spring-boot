package com.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "business")
public class ThreadPoolProperty {
    public List<Map<String, ThreadPool>> threadPool;

    public ThreadPool getThreadPoolByName(String name) {
        return threadPool.stream().filter(tp -> tp.keySet().contains(name))
                .findFirst().map(stringThreadPoolMap -> stringThreadPoolMap.get(name)).get();
    }
}


