/**
 * Created by 尼恩 at 疯狂创客圈
 */

package com.hero.cocurrent;

import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FutureTaskScheduler extends Thread
{
    private final Logger logger = Logger.getLogger(this.getClass());
    private ConcurrentLinkedQueue<ExecuteTask> executeTaskQueue =
            new ConcurrentLinkedQueue<ExecuteTask>();// 任务队列
    private long sleepTime = 200;// 线程休眠时间
    private ExecutorService pool = Executors.newFixedThreadPool(10);

    private static FutureTaskScheduler inst = new FutureTaskScheduler();

    private FutureTaskScheduler()
    {
        this.start();
    }

    /**
     * 添加任务
     *
     * @param executeTask
     */


    public static void add(ExecuteTask executeTask)
    {
        inst.executeTaskQueue.add(executeTask);
    }

    @Override
    public void run()
    {
        while (true)
        {
            handleTask();// 处理任务
            threadSleep(sleepTime);
        }
    }

    private void threadSleep(long time)
    {
        try
        {
            sleep(time);
        } catch (InterruptedException e)
        {
            logger.error(e);
        }
    }

    /**
     * 处理任务队列，检查其中是否有任务
     */
    private void handleTask()
    {
        try
        {
            ExecuteTask executeTask;
            while (executeTaskQueue.peek() != null)
            {
                executeTask = executeTaskQueue.poll();
                handleTask(executeTask);
            }
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    /**
     * 执行任务操作
     *
     * @param executeTask
     */
    private void handleTask(ExecuteTask executeTask)
    {
        pool.execute(new ExecuteRunnable(executeTask));
    }

    class ExecuteRunnable implements Runnable
    {
        ExecuteTask executeTask;

        ExecuteRunnable(ExecuteTask executeTask)
        {
            this.executeTask = executeTask;
        }

        public void run()
        {
            executeTask.execute();
        }
    }
}
