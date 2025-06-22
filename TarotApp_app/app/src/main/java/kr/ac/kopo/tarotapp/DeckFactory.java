package kr.ac.kopo.tarotapp;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import kr.ac.kopo.tarotapp.TarotCard;
import java.util.ArrayList;
import java.util.List;

public class DeckFactory {
    // 카드 생성 (values의 이미지, 이름, 키워드 가져오기)
    public static List<TarotCard> getFullDeck(Context ctx) {
        Resources res = ctx.getResources();
        String[] names = res.getStringArray(R.array.card_names);
        String[] keywords = res.getStringArray(R.array.card_keywords);
        TypedArray imgs = res.obtainTypedArray(R.array.card_images);

        List<TarotCard> deck = new ArrayList<>();
        int count = Math.min(names.length, Math.min(keywords.length, imgs.length()));
        for (int i = 0; i < count; i++) {
            int imgResId = imgs.getResourceId(i, R.drawable.tarot_back);
            deck.add(new TarotCard(names[i], imgResId, keywords[i]));
        }
        imgs.recycle();
        return deck;
    }
}