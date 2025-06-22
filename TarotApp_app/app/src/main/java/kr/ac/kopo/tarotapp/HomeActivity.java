package kr.ac.kopo.tarotapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 주제 버튼 클릭 이벤트 처리
        findViewById(R.id.btn_daily).setOnClickListener(this::openDraw);
        findViewById(R.id.btn_money).setOnClickListener(this::openDraw);
        findViewById(R.id.btn_love).setOnClickListener(this::openDraw);
    }

    private void openDraw(View v) {
        String type = "";
        if (v.getId() == R.id.btn_daily) type = "daily";    // 오늘의 운세
        else if (v.getId() == R.id.btn_money) type = "money";   // 금전운
        else if (v.getId() == R.id.btn_love) type = "love";    // 연애운
        Intent i = new Intent(this, CardDrawActivity.class);
        i.putExtra("fortuneType", type);
        startActivity(i);
    }
}