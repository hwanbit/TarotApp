package kr.ac.kopo.tarotapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import kr.ac.kopo.tarotapp.ApiClient;
import kr.ac.kopo.tarotapp.ApiCallback;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvLoadingText;
    private TextView tvTarotResult;
    private TextView tvCardNames;
    private TextView tvCardKeywords;

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

        // 뷰 초기화
        progressBar     = findViewById(R.id.progress_bar);
        tvTarotResult   = findViewById(R.id.tv_tarot_result);
        tvLoadingText  = findViewById(R.id.tv_loading_text);
        tvCardNames     = findViewById(R.id.tv_card_names);
        tvCardKeywords  = findViewById(R.id.tv_card_keywords);
        ImageView ivCard1 = findViewById(R.id.iv_card1);
        ImageView ivCard2 = findViewById(R.id.iv_card2);
        ImageView ivCard3 = findViewById(R.id.iv_card3);

        // 인텐트에서 카드 리스트 받기
        List<TarotCard> selectedCards =
                (List<TarotCard>) getIntent().getSerializableExtra("selectedCards");

        if (selectedCards != null && selectedCards.size() >= 3) {
            // 카드 이미지, 이름, 키워드 세팅
            TarotCard card1 = selectedCards.get(0);
            TarotCard card2 = selectedCards.get(1);
            TarotCard card3 = selectedCards.get(2);

            ivCard1.setImageResource(card1.getImageResId());
            ivCard2.setImageResource(card2.getImageResId());
            ivCard3.setImageResource(card3.getImageResId());

            tvCardNames.setText(
                    "선택된 카드: " +
                            card1.getName() + " / " +
                            card2.getName() + " / " +
                            card3.getName()
            );
            tvCardKeywords.setText(
                    "카드 키워드: " +
                            card1.getKeywords() + " | " +
                            card2.getKeywords() + " | " +
                            card3.getKeywords()
            );

            // 프롬프트 작성
            StringBuilder sb = new StringBuilder("선택된 타로 카드는 다음과 같습니다:\n");
            sb.append("주제: ").append(subject).append("\n\n");
            for (int i = 0; i < 3; i++) {
                TarotCard card = selectedCards.get(i);
                sb.append("- ").append(card.getName())
                        .append(" (키워드: ").append(card.getKeywords()).append(")\n");
            }
            sb.append("\n위 카드들의 이름과 키워드를 바탕으로, ")
                    .append("\"").append(subject).append("\" 에 초점을 맞춰서 ")
                    .append("각 카드의 의미를 상세히 설명하고, ")
                    .append("세 카드를 조합했을 때의 전반적인 해석을 ")
                    .append("한국어로 300자 내외로 설명해주세요.");

            callInferApi(sb.toString());
        }

        // 다시 선택하기 버튼
        findViewById(R.id.btn_retry).setOnClickListener(v -> {
            Intent home = new Intent(this, HomeActivity.class);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(home);
        });
        // 홈 버튼
        findViewById(R.id.btn_home).setOnClickListener(v -> {
            Intent home = new Intent(this, HomeActivity.class);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(home);
        });
    }

    private void callInferApi(String prompt) {
        // LLM 결과 호출 전: 스피너 보이기 & 결과 초기화
        runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE);
            tvLoadingText.setVisibility(View.VISIBLE);
            tvTarotResult.setText("");
        });

        ApiClient.infer(prompt, new ApiCallback() {
            @Override
            public void onResult(final String response, final Throwable error) {
                runOnUiThread(() -> {
                    // LLM 결과 호출 후: 스피너 숨기기
                    progressBar.setVisibility(View.GONE);
                    tvLoadingText.setVisibility(View.GONE);
                    if (error != null) {
                        tvTarotResult.setText("오류: " + error.getMessage());
                    } else if (response == null || response.isEmpty()) {
                        tvTarotResult.setText("결과가 없습니다.");
                    } else {
                        tvTarotResult.setText(response);
                    }
                });
            }
        });
    }
}