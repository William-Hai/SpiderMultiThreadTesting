package org.demo.spider.multithread.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>
 * 队列，保存将要访问的URL
 * </p>
 * <p>
 * 此处的队列采用组合的方式
 * </p>
 * 2015年12月23日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1.1
 */
public class SpiderQueue {
    // TODO 0.1.2版本中会将此对象设计成单例模式
    
	// 使用链表实现队列
	private Queue<WebInfoModel> mQueue = null;

	private int mMaxSize = 0;
	private double mLowRate = 0.1;
	
	/**
	 * 构造器
	 */
	public SpiderQueue() {
	    if (mQueue == null) {
	        // 请不要更改此处的LinkedList
            mQueue = new LinkedList<WebInfoModel>();
        }
	    
	    mMaxSize = Integer.MAX_VALUE;
	}
	
	/**
	 * 入队列
	 * 
	 * @param model
	 *         待入队的URL对象
	 */
	public synchronized void offer(WebInfoModel model) {
	    while(isQueueFull()) {
	        try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
	    }
	    this.notify();
		mQueue.offer(model);
	}

	/**
	 * 出队列
	 * 
	 * @return
	 *     返回从队列中poll的URL对象
	 */
	public synchronized WebInfoModel poll() {
	    while (isQueueEmpty()) {
	        try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
	    }
	    this.notify();
		return mQueue.poll();
	}

	/**
	 * 判断队列是否为空
	 * 
	 * @return
	 *     返回队列是否为空
	 */
	public synchronized boolean isQueueEmpty() {
	    if (getQueueSize() == 0) {
            return true;
        }
		return false;
	}
	
	/**
	 * 判断队列是否已经满了
	 * 
	 * @return
	 *     判断队列是否已满
	 */
	public synchronized boolean isQueueFull() {
	    if (getQueueSize() >= mMaxSize) {
            return true;
        }
	    
	    return false;
	}
	
	/**
	 * 判断队列中的数据是否已经低于某一个值
	 * 
	 * @return
	 *         返回队列数据是否过低
	 */
    public synchronized boolean isQueueLow() {
        if (getQueueSize() <= mLowRate * getMaxSize()) {
            return true;
        }
        
        return false;
    }
	
	/**
	 * 判断队列是否包含对象o
	 * 
	 * @param o
	 *         待判断的对象
	 * @return
	 *         是否包含
	 */
	public synchronized boolean contians(Object o) {
		return mQueue.contains(o);
	}

	/**
	 * 获得队列的长度
	 * 
	 * @return
	 *     队列长度
	 */
	public synchronized int getQueueSize() {
		if (mQueue == null) {
			return 0;
		}
		
		return mQueue.size();
	}
	
	/**
	 * 获得队列
	 * 
	 * @return
	 *     返回队列
	 */
	public synchronized Queue<WebInfoModel> getQueueLinkedList() {
		return mQueue;
	}

	/**
	 * 获得队列的最大长度
	 * 
	 * @return
	 *     队列的最大长度
	 */
    public int getMaxSize() {
        return mMaxSize;
    }

    /**
     * 设置队列的最大长度
     * 
     * @param maxSize
     *          队列的最大长度
     */
    public void setMaxSize(int maxSize) {
        this.mMaxSize = maxSize;
    }
}
