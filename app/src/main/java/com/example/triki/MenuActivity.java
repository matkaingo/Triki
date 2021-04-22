package com.example.triki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button BSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void Clasico(View view){
        Intent intent = new Intent(this,ClasicoActivity.class);
        startActivity(intent);
    }

    public void Single(View view){
        Intent intent = new Intent(this,SingleActivity.class);
        startActivity(intent);
    }

    public void Cerrar(View view){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}