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

        findViewById(R.id.btn_daily).setOnClickListener(this::openDraw);
        findViewById(R.id.btn_money).setOnClickListener(this::openDraw);
        findViewById(R.id.btn_love).setOnClickListener(this::openDraw);
    }

    private void openDraw(View v) {
        String type = "";
        if (v.getId() == R.id.btn_daily) type = "daily";
        else if (v.getId() == R.id.btn_money) type = "money";
        else if (v.getId() == R.id.btn_love) type = "love";
        Intent i = new Intent(this, CardDrawActivity.class);
        i.putExtra("fortuneType", type);
        startActivity(i);
    }
}