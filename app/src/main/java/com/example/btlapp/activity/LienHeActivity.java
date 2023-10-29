package com.example.btlapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.btlapp.R;

import java.util.Objects;

public class LienHeActivity extends AppCompatActivity {
    Toolbar toolbarlienhe;

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
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbarlienhe.setNavigationOnClickListener(v -> finish());

    }
}