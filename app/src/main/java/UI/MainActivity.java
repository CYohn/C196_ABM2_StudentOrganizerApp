package UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.c196_abm2_charity_yohn.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void allTermsBtnPressed(View view){
        Intent intent = new Intent(MainActivity.this, UI.AllTermsList.class);
        startActivity(intent);
    }
}