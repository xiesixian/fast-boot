package com.xiesx.fastboot.support.executor;

import java.util.concurrent.ExecutionException;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.xiesx.fastboot.base.result.BaseResult;
import com.xiesx.fastboot.base.result.R;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutorHelperTest {

    @Test
    public void executor() throws InterruptedException, ExecutionException {
        // 执行任务,不返回结果（方式1）
        ExecutorHelper.submit(new MyRunnable("1"));
        // 执行任务,监听结果（方式2）
        ExecutorHelper.submit(() -> R.succ("2"), new MyFutureCallback());
        // 执行任务,监听结果（方式3）
        ExecutorHelper.submit(new MyTask("3"));
        // 执行任务,返回结果（方式4）
        ListenableFuture<String> future1 = ExecutorHelper.submit(() -> "4");
        ListenableFuture<String> future2 = Futures.transform(future1, new Function<String, String>() {

            @Override
            public String apply(String input) {
                return input + " transform";
            }
        }, MoreExecutors.directExecutor());

        Futures.addCallback(future2, new FutureCallback<String>() {

            @Override
            public void onSuccess(@Nullable String result) {
                log.info(result);
            }

            @Override
            public void onFailure(Throwable t) {
                log.info(t.getMessage());
            }
        }, MoreExecutors.directExecutor());
    }

    public static class MyRunnable implements Runnable {

        public String val;

        public MyRunnable(String val) {
            this.val = val;
        }

        @Override
        public void run() {
            // xxx
            log.info(val);
        }
    }

    public static class MyFutureCallback implements FutureCallback<BaseResult> {

        @Override
        public void onSuccess(@Nullable BaseResult result) {
            log.info(result.getMsg());
        }

        @Override
        public void onFailure(Throwable t) {
            log.info(t.getMessage());
        }
    }

    @AllArgsConstructor
    public static class MyTask extends DefaultTask<BaseResult> {

        public String keyword;

        @Override
        public BaseResult call() throws Exception {
            // xxx
            return R.succ(keyword);
        }

        @Override
        public void onSuccess(BaseResult result) {
            log.info(result.getMsg());
        }

        @Override
        public void onFailure(Throwable t) {
            log.info(t.getMessage());
        }
    }

}
