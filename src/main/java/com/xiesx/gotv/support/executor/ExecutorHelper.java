package com.xiesx.gotv.support.executor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * @title 线程池
 * @description
 *              newCachedThreadPool：缓存型,先查看池中有没有以前建立的线程，如果有，就reuse；如果没有，就建一个新的线程加入池中。
 *              newFixedThreadPool：固定型,可控制线程最大并发数，超出的线程会在队列中等待。
 *              ScheduledThreadPool 调度型,支持定时及周期性任务执行。
 *              SingleThreadExecutor 单例型,它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 * @author Sixian.xie
 * @date 2018年7月23日 下午4:30:20
 */
@Slf4j
public class ExecutorHelper {

	private static final String TAG = ExecutorHelper.class.getSimpleName();

	/**
	 * 缓存型线程池
	 */
	private static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

	/**
	 * 缓存型线程池
	 */
	private static ListeningExecutorService service_data = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

	/**
	 * 添加异步计算任务
	 * 
	 * @param task
	 * @return
	 */
	public static <T> ListenableFuture<T> submit(DefaultTask<T> task) {
		return submit(task, task);
	}

	// --------------------

	/**
	 * 添加异步任务，当有结果了进行回调
	 * 
	 * @param task
	 * @param callback
	 * @return
	 */
	public static ListenableFuture<?> submit(Runnable task) {
		return service.submit(task);
	}

	/**
	 * 添加异步任务，当有结果了进行回调
	 * 
	 * @param task
	 * @param callback
	 * @return
	 */
	public static <T> ListenableFuture<T> submit(Callable<T> task, FutureCallback<T> callback) {
		ListenableFuture<T> future = service.submit(task);
		if (callback != null) {
			// Futures.addCallback(future, callback);
		}
		return future;
	}

	/**
	 * 批量执行任务（所有）
	 * 
	 * @see invokeAll()在所有任务都完成（包括成功/被中断/超时）后才会返回。有不限时和限时版本，从更简单的不限时版入手。
	 * @param task
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static <T> List<Future<T>> invokeAll(List<DefaultTask<T>> tasks) {
		try {
			return service.invokeAll(tasks);
		} catch (InterruptedException e) {
			log.error(TAG, e);
		}
		return null;
	}

	// --------------------

	/**
	 * 停止
	 */
	public static void shutdownNow2() {
		// shutdown，执行后不再接收新任务，如果里面有任务，就执行完
		// shutdownNow，执行后不再接受新任务，如果有等待任务，移出队列；有正在执行的，尝试停止service_data.shutdownNow();
		service_data.shutdownNow();
	}

	public static ListeningExecutorService getService2() {
		if (service_data.isShutdown()) {
			service_data = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
		}
		return service_data;
	}
}
