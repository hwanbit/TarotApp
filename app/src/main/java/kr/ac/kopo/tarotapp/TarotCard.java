package kr.ac.kopo.tarotapp;

import java.io.Serializable;

public class TarotCard implements Serializable {
    private String name;
    private int imageResId;
    private String keywords;

    public TarotCard(String name, int imageResId, String keywords) {
        this.name = name;
        this.imageResId = imageResId;
        this.keywords = keywords;
    }

    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public String getKeywords() { return keywords; }
}
