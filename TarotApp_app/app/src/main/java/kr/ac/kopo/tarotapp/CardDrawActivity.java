package kr.ac.kopo.tarotapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class CardDrawActivity extends AppCompatActivity {
    private List<TarotCard> deck;
    private final List<TarotCard> selected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_draw);

        // 1. 인텐트에서 fortuneType 값을 받기
        String fortuneType = getIntent().getStringExtra("fortuneType");

        // 2. fortuneType에 따라 표시할 텍스트 결정
        String subject = "오늘의 운세"; // 기본값
        if ("daily".equals(fortuneType)) subject = "오늘의 운세";
        else if ("money".equals(fortuneType)) subject = "금전운";
        else if ("love".equals(fortuneType)) subject = "연애운";

        // 3. TextView에 표시
        TextView tvSubject = findViewById(R.id.tv_subject);
        tvSubject.setText(subject);

        // 빈 GridLayout 초기화
        GridLayout grid = findViewById(R.id.grid_cards);
        grid.removeAllViews();

        // 덱 데이터 로드 및 그리드 속성 설정
        deck = DeckFactory.getFullDeck(this);
        int columnCount = 5;
        grid.setColumnCount(columnCount);
        // 22장의 카드를 매번 랜덤하게 섞기
        Collections.shuffle(deck);

        // 카드 뷰 동적 추가
        for (int i = 0; i < deck.size(); i++) {
            TarotCard card = deck.get(i);
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.tarot_back);
            iv.setTag(card);
            iv.setPadding(8, 8, 8, 8);
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            // 각 셀에 맞춘 LayoutParams (행, 열, 가중치)
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(i / columnCount, 1f),
                    GridLayout.spec(i % columnCount, 1f)
            );
            params.width = 0;
            params.height = 0;
            params.setMargins(3, 3, 3, 3);
            iv.setLayoutParams(params);

            iv.setOnClickListener(this::onCardClick);
            grid.addView(iv);
        }
    }

    private void onCardClick(View v) {
        TarotCard card = (TarotCard) v.getTag();
        AlphaAnimation anim = new AlphaAnimation(1f, 0f);
        anim.setDuration(300);
        anim.setFillAfter(true);
        v.startAnimation(anim);
        v.setEnabled(false);
        selected.add(card);

        // 선택한 카드가 3장이면 결과 화면으로 이동
        if (selected.size() == 3) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("selectedCards", (Serializable) selected);

            String fortuneType = getIntent().getStringExtra("fortuneType");
            intent.putExtra("fortuneType", fortuneType);

            startActivity(intent);
            finish();
        }

    // 홈 아이콘 클릭 시 홈으로 이동
    findViewById(R.id.btn_home).setOnClickListener(view -> {
        // 홈 이동 코드
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(homeIntent);
    });
    }
}