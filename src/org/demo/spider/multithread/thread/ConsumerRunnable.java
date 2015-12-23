package org.demo.spider.multithread.thread;

import org.demo.spider.multithread.model.SpiderQueue;
import org.utils.naga.nums.RandomUtils;
import org.utils.naga.threads.ThreadUtils;

/**
 * <p>
 * 消费者线程
 * </p>
 * 2015年12月23日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class ConsumerRunnable implements Runnable {

    private SpiderQueue spiderQueue = null;
    
    public ConsumerRunnable(SpiderQueue _spiderQueue) {
        spiderQueue = _spiderQueue;
    }
    
    @Override
    public void run() {
        if (spiderQueue == null) {
            return;
        }
        
        RandomUtils random = new RandomUtils();
        ThreadUtils.sleep(random.nextInt(400, 800));
        
        while(!spiderQueue.isQueueEmpty()) {
            spiderQueue.poll();
            ThreadUtils.sleep(2);
        }
        
        System.out.println("--消费者已经消费掉一批产品");
    }

}
