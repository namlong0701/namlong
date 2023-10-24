package com.example.btlapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.example.btlapp.R;

public class LienHeActivity extends AppCompatActivity {
    Toolbar toolbarlienhe;
    TextView txtdiachilienhe,txtsdtlh,txtthongtin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        Anhxa();
        ActionToolbar();
    }

    private void Anhxa() {
        toolbarlienhe= (Toolbar) findViewById(R.id.toolbarlienhe);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarlienhe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlienhe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}