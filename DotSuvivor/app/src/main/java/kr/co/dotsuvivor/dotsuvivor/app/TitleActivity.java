package kr.co.dotsuvivor.dotsuvivor.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import kr.co.dotsuvivor.R;


public class TitleActivity extends AppCompatActivity {
    private static final String TAG = TitleActivity.class.getSimpleName();
    //private ActivityTitleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }
    public void onBtnStartGame(View view) {
        Log.d(TAG, "Starting game main");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
