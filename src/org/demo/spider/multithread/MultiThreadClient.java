package org.demo.spider.multithread;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.demo.spider.multithread.db.DBHelper;
import org.demo.spider.multithread.model.SpiderQueue;
import org.demo.spider.multithread.thread.ConsumerRunnable;
import org.demo.spider.multithread.thread.InsertDBRunnable;
import org.demo.spider.multithread.thread.LocalParserRunnable;
import org.demo.spider.multithread.thread.ParserRunner;
import org.demo.spider.multithread.thread.ProducerRunnable;
import org.utils.naga.nums.RandomUtils;
import org.utils.naga.threads.ThreadUtils;

public class MultiThreadClient {

    private ThreadPoolExecutor mThreadPool;
    
    private DBHelper helper = null;
    
    private void initEvent() {
        if (mThreadPool == null) {
            mThreadPool = getThreadPool();
        }
        
        if (helper == null) {
            helper = new DBHelper();
        }
    }
    
    private ThreadPoolExecutor getThreadPool() {
        final int MAXIMUM_POOL_SIZE = 10;
        final int CORE_POOL_SIZE = 7;
        return new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(MAXIMUM_POOL_SIZE), new ThreadPoolExecutor.DiscardOldestPolicy());
    }
    
    /**
     * 判断线程池中的线程队列是否已经满了
     * 
     * @param threadPool
     *         线程池对象
     */
    private void poolQueueFull(ThreadPoolExecutor threadPool) {
        while (getQueueSize(threadPool.getQueue()) >= threadPool.getMaximumPoolSize()) {
            System.err.println("线程池队列已满，等" + (3 * 1000) + "毫秒再添加任务");
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private synchronized int getQueueSize(Queue<Runnable> queue) {
        return queue.size();
    }
    
    // 测试只解析Web网页的线程运行情况
    private void testMultiThreadParser(int times) {
        for (int i = 0; i < times; i++) {
            poolQueueFull(mThreadPool);
            mThreadPool.execute(new ParserRunner("http://www.cnblogs.com/Stone-sqrt3/", i + 1));
        }
    }
    
    // 测试只向数据库中插入数据的线程运行情况
    private void testMultiThreadInsert(int count) {
        RandomUtils random = new RandomUtils();
        for (int i = 0; i < count; i++) {
            mThreadPool.execute(new InsertDBRunnable(helper, random.nextInt(10, 200), i + 1));
            ThreadUtils.sleep(3);
        }
    }
    
    // 测试只解析本地HTML文件的线程运行情况
    private void testMultiThreadLocal(int count) {
        String localHTML = "F:/Temp/cnblogs.html";
        for (int i = 0; i < count; i++) {
            mThreadPool.execute(new LocalParserRunnable(helper, localHTML, i));
            ThreadUtils.sleep(1);
        }
    }
    
    // 测试生产者与消费者的线程运行情况
    private void testPCMultiThread(int count) {
        SpiderQueue queue = new SpiderQueue();
        RandomUtils random = new RandomUtils();
        
        for (int i = 0; i < count; i++) {
            mThreadPool.execute(new ProducerRunnable(queue));
            mThreadPool.execute(new ConsumerRunnable(queue));
            
            ThreadUtils.sleep(random.nextInt(300, 500));
        }
    }
    
    private void testMulti() {
        int testFlag = 3;
        switch (testFlag) {
        case 0:
            // 多线程解析
            testMultiThreadParser(1000000000);
            break;
            
        case 1:
            // 多线程入库
            testMultiThreadInsert(1000000000);
            break;
            
        case 2:
            // 本地HTML解析并入库
            testMultiThreadLocal(1000000000);
            break;
            
        case 3:
            // 生产者与消费者
            testPCMultiThread(1000000000);
            break;

        default:
            break;
        }
    }
    
    public static void main(String[] args) {
        MultiThreadClient client = new MultiThreadClient();
        client.initEvent();
        client.testMulti();
    }
}
