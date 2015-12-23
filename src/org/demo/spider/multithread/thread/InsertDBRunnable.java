package org.demo.spider.multithread.thread;

import org.demo.spider.multithread.db.DBHelper;
import org.demo.spider.multithread.model.WebInfoModel;
import org.utils.naga.nums.RandomUtils;
import org.utils.naga.str.StringUtils;

/**
 * <p>
 * 随机生成URL并入库
 * </p>
 * 2015年12月14日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class InsertDBRunnable implements Runnable {

    private final String URL_TEMPLATE = "http://www.xxx.com/";
    private int urlCount = 0;
    private int index = 0;
    private WebInfoModel[] models = null;
    
    private DBHelper helper = null;
    
    public InsertDBRunnable(DBHelper _helper, int _count, int _index) {
        helper = _helper;
        urlCount = _count;
        index = _index;
        initEvent();
    }
    
    private void initEvent() {
        models = new WebInfoModel[urlCount];
    }
    
    private void initArray() {
        int length = models.length;
        RandomUtils randomUtils = new RandomUtils();
        for (int i = 0; i < length; i++) {
            String random = StringUtils.randomString(8);
            models[i] = new WebInfoModel();
            models[i].setAddress(URL_TEMPLATE.replace("xxx", random));
            models[i].setLevel(randomUtils.nextInt(1, 8));
            models[i].setName(random);
        }
    }
    
    @Override
    public void run() {
        initArray();
        helper.insertModelArray(models);
        System.out.println("第" + index + "批向数据库中添加了" + urlCount + "条数据.");
    }
}
