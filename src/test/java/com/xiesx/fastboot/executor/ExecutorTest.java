package com.xiesx.fastboot.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.util.concurrent.FutureCallback;
import com.xiesx.fastboot.base.result.BaseResult;
import com.xiesx.fastboot.base.result.R;
import com.xiesx.fastboot.support.executor.DefaultTask;
import com.xiesx.fastboot.support.executor.ExecutorHelper;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ExecutorTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //
        ExecutorHelper.submit(new Callable<BaseResult>() {

            @Override
            public BaseResult call() throws Exception {
                return R.succ("1");
            }
        }, new FutureCallback<BaseResult>() {

            @Override
            public void onSuccess(@Nullable BaseResult result) {
                System.out.println(result.getMsg());
            }

            @Override
            public void onFailure(Throwable t) {}

        });
        ExecutorHelper.submit(new MyTask2("2"));
        ExecutorHelper.submit(new MyRunnable3("3"));

        Demo demo = new Demo();
        ExecutorHelper.submit(new MyRunnable4("4"), demo).get().getA();
    }

    @AllArgsConstructor
    public static class MyTask2 extends DefaultTask<BaseResult> {

        public String keyword;

        /**
         * 执行
         */
        @Override
        public BaseResult call() throws Exception {
            return R.succ(keyword);
        }

        @Override
        public void onSuccess(BaseResult result) {
            System.out.println(result.getMsg());
        }
    }

    public static class MyRunnable3 implements Runnable {

        public String val;

        public MyRunnable3(String val) {
            this.val = val;
        }

        @Override
        public void run() {
            System.out.println(val);
        }
    }

    public static class MyRunnable4 implements Runnable {

        public Demo demo;

        public String val;

        public MyRunnable4(String val) {
            this.val = val;
            this.demo = new Demo();
        }

        @Override
        public void run() {

            demo.setA(val);
            System.out.println(demo.getA());
        }
    }

    @Data
    public static class Demo {

        public String a;
    }

}
