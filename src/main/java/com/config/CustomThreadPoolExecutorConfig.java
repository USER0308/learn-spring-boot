package com.config;

import com.common.ThreadPoolExecutorWrapper;
import com.property.ThreadPool;
import com.property.ThreadPoolProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池，包括三种线程池，通用线程池、io密集型线程池，计算密集型线程池
 * 理论上来说，io密集型线程池核心数设置为cpu数量的2倍，计算密集型线程池核心线程数设置为cpu数量
 */
@Component
public class CustomThreadPoolExecutorConfig {
    @Autowired
    private ThreadPoolProperty threadPoolProperty;

    @Bean(name = "commonThreadPoolExecutor")
    public ThreadPoolExecutor commonThreadPoolExecutor() {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue();
        ThreadPool threadPool = threadPoolProperty.getThreadPoolByName("common");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutorWrapper(threadPool.getCorePoolSize(),
                threadPool.getMaximumPoolSize(),
                threadPool.getKeepAlive(),
                TimeUnit.SECONDS, blockingQueue);
        return threadPoolExecutor;
    }

    @Bean(name = "ioThreadPoolExecutor")
    public ThreadPoolExecutor ioThreadPoolExecutor() {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue();
        ThreadPool threadPool = threadPoolProperty.getThreadPoolByName("io");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutorWrapper(threadPool.getCorePoolSize(),
                threadPool.getMaximumPoolSize(),
                threadPool.getKeepAlive(),
                TimeUnit.SECONDS, blockingQueue);
        return threadPoolExecutor;
    }

    @Bean(name = "computingThreadPoolExecutor")
    public ThreadPoolExecutor computingThreadPoolExecutor() {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue();
        ThreadPool threadPool = threadPoolProperty.getThreadPoolByName("computing");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutorWrapper(threadPool.getCorePoolSize(),
                threadPool.getMaximumPoolSize(),
                threadPool.getKeepAlive(),
                TimeUnit.SECONDS, blockingQueue);
        return threadPoolExecutor;
    }
}
