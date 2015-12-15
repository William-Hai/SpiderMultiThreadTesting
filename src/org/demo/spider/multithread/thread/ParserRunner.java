package org.demo.spider.multithread.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.utils.naga.web.impl.WebHTMLParserImpl;
import org.utils.naga.web.poke.HTMLParserStrategy;

/**
 * <p>
 * 解析URL线程
 * </p>
 * 2015年12月14日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class ParserRunner implements Runnable {

    private HTMLParserStrategy htmlParserStrategy;
    private int index;
    private String url;
    private List<String> subUrlList;
    
    public ParserRunner(String _url, int _index) {
        url = _url;
        index = _index;
        
        subUrlList = new ArrayList<>();
        htmlParserStrategy = new HTMLParserStrategy(new WebHTMLParserImpl());
    }
    
    @Override
    public void run() {
        try {
            Document document = htmlParserStrategy.requestHTML(url, 30000);
            Elements contents = document.getElementsByTag("a");
            for (Element element : contents) {
                subUrlList.add(element.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("线程[" +index + "]" + "已经完成.");
    }

}
