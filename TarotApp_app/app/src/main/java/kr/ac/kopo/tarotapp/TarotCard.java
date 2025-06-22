package kr.ac.kopo.tarotapp;

import java.io.Serializable;

public class TarotCard implements Serializable {
    private String name;    // 카드 이름
    private int imageResId;    // 카드 이미지 리소스 ID
    private String keywords;    // 카드 키워드

    public TarotCard(String name, int imageResId, String keywords) {
        this.name = name;
        this.imageResId = imageResId;
        this.keywords = keywords;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public String getKeywords() { return keywords; }
}
