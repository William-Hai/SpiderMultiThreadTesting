package org.demo.spider.multithread;

import org.utils.naga.str.StringUtils;

public class Test {

    private final String URL_TEMPLATE = "http://www.xxx.com/";
    
    public static void main(String[] args) {
        new Test().test();
    }
    
    private void test() {
        String tmpURL = URL_TEMPLATE.replace("xxx", StringUtils.randomString(8));
        System.out.println(tmpURL);
        System.out.println(URL_TEMPLATE);
    }
}
