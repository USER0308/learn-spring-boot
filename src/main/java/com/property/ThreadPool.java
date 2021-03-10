package com.property;

import lombok.Data;

@Data
public class ThreadPool {
    private int corePoolSize;
    private int maximumPoolSize;
    private int keepAlive;
}
