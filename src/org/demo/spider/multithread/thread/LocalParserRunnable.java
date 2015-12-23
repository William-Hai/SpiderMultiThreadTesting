package org.demo.spider.multithread.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.demo.spider.multithread.db.DBHelper;
import org.demo.spider.multithread.model.WebInfoModel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.utils.naga.nums.RandomUtils;
import org.utils.naga.str.StringUtils;
import org.utils.naga.web.impl.LocalHTMLParserImpl;
import org.utils.naga.web.poke.HTMLParserStrategy;

/**
 * <p>
 * 解析本地HTML并入库
 * </p>
 * 2015年12月15日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class LocalParserRunnable implements Runnable {

    private HTMLParserStrategy htmlParserStrategy;
    private String localHTMLPath = "";
    private List<WebInfoModel> subUrlList;
    private int index;
    private DBHelper helper = null;
    
    public LocalParserRunnable(DBHelper _helper, String _localHTMLPath, int _index) {
        helper = _helper;
        localHTMLPath = _localHTMLPath;
        subUrlList = new ArrayList<>();
        index = _index;
        
        htmlParserStrategy = new HTMLParserStrategy(new LocalHTMLParserImpl());
    }
    
    @Override
    public void run() {
        try {
            Document document = htmlParserStrategy.requestHTML(localHTMLPath);
            Elements contents = document.getElementsByTag("a");
            RandomUtils random = new RandomUtils();
            WebInfoModel model = null;
            for (Element element : contents) {
                model = new WebInfoModel();
                model.setName(StringUtils.randomString(10));
                model.setAddress(element.attr("href"));
                model.setLevel(random.nextInt(1, 8));
                
                subUrlList.add(model);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        helper.insertModelList(subUrlList);
        
        System.out.println("线程[" +index + "]" + "已经完成.");
    }

}
