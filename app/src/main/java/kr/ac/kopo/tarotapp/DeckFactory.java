package kr.ac.kopo.tarotapp;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import kr.ac.kopo.tarotapp.TarotCard;
import java.util.ArrayList;
import java.util.List;

public class DeckFactory {
    public static List<TarotCard> getFullDeck(Context ctx) {
//        List<TarotCard> list = new ArrayList<>();
//        list.add(new TarotCard("The Fool (광대)", R.drawable.the_fool, "모험, 순수"));
//        list.add(new TarotCard("The Magician (마술사)", R.drawable.the_magician, "결정, 창조"));
//        list.add(new TarotCard("The High Priestess (여교황)", R.drawable.the_high_priestess, "지식, 총명"));
//        list.add(new TarotCard("The Empress (여황제)", R.drawable.the_empress, "풍요, 모성"));
//        list.add(new TarotCard("The Emperor (황제)", R.drawable.the_emperor, "책임, 부성"));
//        list.add(new TarotCard("The Hierophant (교황)", R.drawable.the_hierophant, "가르침, 관대함"));
//        list.add(new TarotCard("The Lovers (연인)", R.drawable.the_lovers, "연애, 애정"));
//        list.add(new TarotCard("The Chariot (전차)", R.drawable.the_chariot, "전진, 승리"));
//        list.add(new TarotCard("Strength (힘)", R.drawable.strength, "힘, 용기"));
//        list.add(new TarotCard("The Hermit (은둔자)", R.drawable.the_hermit, "탐색, 사려깊음"));
//        list.add(new TarotCard("Wheel of Fortune (운명의 수레바퀴)", R.drawable.wheel_of_fortune, "기회, 행운"));
//        list.add(new TarotCard("Justice (정의)", R.drawable.justice, "균형, 정당함"));
//        list.add(new TarotCard("The Hanged Man (매달린 남자)", R.drawable.the_hanged_man, "희생, 인내"));
//        list.add(new TarotCard("Death (죽음)", R.drawable.death, "격변, 이별"));
//        list.add(new TarotCard("Temperance (절제)", R.drawable.temperance, "조화, 견실"));
//        list.add(new TarotCard("The Devil (악마)", R.drawable.the_devil, "속박, 타락"));
//        list.add(new TarotCard("The Tower (탑)", R.drawable.the_tower, "파괴, 멸망"));
//        list.add(new TarotCard("The Star (별)", R.drawable.the_star, "희망, 동경"));
//        list.add(new TarotCard("The Moon (달)", R.drawable.the_moon, "불안, 혼돈"));
//        list.add(new TarotCard("The Sun (태양)", R.drawable.the_sun, "밝은 미래, 만족"));
//        list.add(new TarotCard("Judgement (심판)", R.drawable.judgement, "부활, 개선"));
//        list.add(new TarotCard("The World (세계)", R.drawable.the_world, "완성, 완전"));
//
//        return list;
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