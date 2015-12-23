package org.demo.spider.multithread.thread;

import org.demo.spider.multithread.model.SpiderQueue;
import org.demo.spider.multithread.model.WebInfoModel;
import org.utils.naga.nums.RandomUtils;
import org.utils.naga.str.StringUtils;
import org.utils.naga.threads.ThreadUtils;

/**
 * <p>
 * 生产者线程
 * </p>
 * 2015年12月23日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class ProducerRunnable implements Runnable {

    private SpiderQueue spiderQueue = null;
    
    public ProducerRunnable(SpiderQueue _spiderQueue) {
        spiderQueue = _spiderQueue;
    }
    
    @Override
    public void run() {
        if (spiderQueue == null) {
            return;
        }
        
        RandomUtils random = new RandomUtils();
        ThreadUtils.sleep(random.nextInt(300, 800));
        
        int length = random.nextInt(10, 100);
        for (int i = 0; i < length; i++) {
            WebInfoModel model = new WebInfoModel();
            model.setName(StringUtils.randomString(10));
            model.setAddress("http://www.abcdef.com/" + model.getName());
            model.setLevel(random.nextInt());
            
            spiderQueue.offer(model);
            
            ThreadUtils.sleep(3);
        }
        
        System.out.println("++生产者已经生成完成一批产品");
    }
}
