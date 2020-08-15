package com.xiesx.fastboot.support.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import com.google.common.util.concurrent.FutureCallback;
import com.xiesx.fastboot.base.result.BaseResult;
import com.xiesx.fastboot.base.result.R;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutorHelperTest {

    @Test
    public void executor() throws InterruptedException, ExecutionException {
        //
        ExecutorHelper.submit(new MyRunnable3("3"));
        //
        ExecutorHelper.submit(new Callable<BaseResult>() {

            @Override
            public BaseResult call() throws Exception {
                return R.succ("1");
            }
        }, new FutureCallback<BaseResult>() {

            @Override
            public void onSuccess(@Nullable BaseResult result) {
                log.info(result.getMsg());
            }

            @Override
            public void onFailure(Throwable t) {}

        });
        //
        ExecutorHelper.submit(new MyTask2("2"));
    }

    @AllArgsConstructor
    public static class MyTask2 extends DefaultTask<BaseResult> {

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
    }

    public static class MyRunnable3 implements Runnable {

        public String val;

        public MyRunnable3(String val) {
            this.val = val;
        }

        @Override
        public void run() {
            // xxx
            log.info(val);
        }
    }
}
