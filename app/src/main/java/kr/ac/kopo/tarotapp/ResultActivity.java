package kr.ac.kopo.tarotapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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

        // 카드 이미지 3장 뷰
        ImageView ivCard1 = findViewById(R.id.iv_card1);
        ImageView ivCard2 = findViewById(R.id.iv_card2);
        ImageView ivCard3 = findViewById(R.id.iv_card3);

        // 타로 LLM 결과 출력 뷰
        TextView tvTarotResult = findViewById(R.id.tv_tarot_result);

        // 인텐트에서 값 가져오기
        Intent intent = getIntent();
        int card1ResId = intent.getIntExtra("card1ResId", R.drawable.tarot_back);
        int card2ResId = intent.getIntExtra("card2ResId", R.drawable.tarot_back);
        int card3ResId = intent.getIntExtra("card3ResId", R.drawable.tarot_back);

        String tarotResult = intent.getStringExtra("tarotResult");

        // 카드 이미지 세팅
        ivCard1.setImageResource(card1ResId);
        ivCard2.setImageResource(card2ResId);
        ivCard3.setImageResource(card3ResId);

        // 타로 결과 세팅
        tvTarotResult.setText(tarotResult != null ? tarotResult : "");

        // 다시 선택하기 클릭 시 홈으로 이동 등
        findViewById(R.id.btn_retry).setOnClickListener(view -> {
            // 홈 이동 코드
            Intent homeIntent = new Intent(this, HomeActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(homeIntent);
        });

        // 홈 아이콘 클릭 시 홈으로 이동
        findViewById(R.id.btn_home).setOnClickListener(view -> {
            // 홈 이동 코드
            Intent homeIntent = new Intent(this, HomeActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(homeIntent);
        });
    }
}