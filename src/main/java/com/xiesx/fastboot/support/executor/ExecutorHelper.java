package com.xiesx.fastboot.support.executor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.util.concurrent.*;

/**
 * @title ExecutorHelper.java
 * @description 线程池 newCachedThreadPool：缓存型,先查看池中有没有以前建立的线程，如果有，就reuse；如果没有，就建一个新的线程加入池中。
 *              newFixedThreadPool：固定型,可控制线程最大并发数，超出的线程会在队列中等待。 ScheduledThreadPool
 *              调度型,支持定时及周期性任务执行。 SingleThreadExecutor 单例型,它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO,
 *              优先级)执行。
 * @author Sixian.xie
 * @date 2020-7-21 22:40:11
 */
public class ExecutorHelper {

    /**
     * 缓存型线程池
     */
    private static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static ListeningExecutorService getService() {
        if (service.isShutdown()) {
            service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        }
        return service;
    }

    public static ListenableFuture<?> submit(Runnable task) {
        return service.submit(task);
    }

    public static <T> ListenableFuture<T> submit(Callable<T> task) {
        return service.submit(task);
    }

    public static <T> ListenableFuture<T> submit(Callable<T> task, FutureCallback<T> callback) {
        ListenableFuture<T> future = service.submit(task);
        if (callback != null) {
            Futures.addCallback(future, callback, MoreExecutors.directExecutor());
        }
        return future;
    }

    public static <T> ListenableFuture<T> submit(DefaultTask<T> task) {
        return submit(task, task);
    }

    public static <T> List<Future<T>> invokeAll(List<DefaultTask<T>> tasks) {
        try {
            return service.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void shutdown() {
        // shutdown，执行后不再接收新任务，如果里面有任务，就执行完
        service.shutdown();
    }

    public static void shutdownNow() {
        // shutdownNow，执行后不再接受新任务，如果有等待任务，移出队列；有正在执行的，尝试停止service_data.shutdownNow();
        service.shutdownNow();
    }

}
