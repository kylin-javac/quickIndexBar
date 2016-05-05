package ligang.huse.cn.quickindexbar.bean;

import ligang.huse.cn.quickindexbar.PinYinUtil;

/**
 * 创建时间 javac on 2016/5/5.
 * <p/>
 * 文  件 quickIndexBar
 * <p/>
 * 描  述 联系人数据库
 */
public class Friend implements Comparable<Friend> {
    private String name;
    private String pinyin;

    public Friend(String name) {
        super();
        this.name = name;
        setPinyin(PinYinUtil.getPinyin(name));
    }
    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Friend another) {
        return getPinyin().compareTo(another.getPinyin());
    }
}
