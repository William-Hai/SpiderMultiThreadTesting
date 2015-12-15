package org.demo.spider.multithread.model;

/**
 * 保存网址的信息
 */
public class WebInfoModel {

	private String name; // 网站名称
	
	private String address; // 网站地址
	
	private int level; // 访问的层次

	public WebInfoModel() {
	}
	
	public WebInfoModel(String name, String url, int level) {
	    this.name = name;
	    this.address = url;
	    this.level = level;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String addres) {
		this.address = addres;
	}
	
	public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
	public String toString() {
		return "[" + name.trim() + ", " + address.trim() + ", " + level + "]";
	}
    
    @Override
    public int hashCode() {
        return (name.hashCode() + address.hashCode() + level);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WebInfoModel)) {
            return false;
        }
        
        if (((WebInfoModel)obj).getName() == name && ((WebInfoModel)obj).getAddress() == address && ((WebInfoModel)obj).getLevel() == level) {
            return true;
        }
        
        return false;
    }
}
