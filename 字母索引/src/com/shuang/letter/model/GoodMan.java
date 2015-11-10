package com.shuang.letter.model;

import com.shuang.letter.utils.PinYinUtil;

public class GoodMan implements Comparable<GoodMan>{
    private String mName;
    private String mPinyin;
    
    public GoodMan(String name) {
        mName = name;
        mPinyin = PinYinUtil.toPinyin(name);
    }
    
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getPinyin() {
        return mPinyin;
    }
    public void setPinyin(String pinyin) {
        mPinyin = pinyin;
    }

    @Override
    public int compareTo(GoodMan another) {
        return mPinyin.compareTo(another.getPinyin());
    }
}
