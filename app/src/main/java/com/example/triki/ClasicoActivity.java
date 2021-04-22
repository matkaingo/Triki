package com.example.triki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClasicoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView playerOneScore, playerTwoScore;
    private Button[] buttons = new Button[9];
    private Button reset;
    private Button menu;

    private int playerOneScoreCount, playerTwoScoreCount,rountCount;
    boolean player;


    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] positions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //filas
            {0,3,6}, {1,4,7}, {2,5,8}, //columnas
            {0,4,8}, {2,4,6} //diagonales
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasico);

        playerOneScore = (TextView)findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView)findViewById(R.id.playerTwoScore);

        reset = (Button) findViewById(R.id.reset);

        for(int i=0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID,"id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        player = true;
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if(player){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }else {
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        rountCount++;
        if(checkWinner()){
            if(player){
                playerOneScoreCount++;
                playerScore();
                Toast.makeText(this,"Jugador 1 Ha ganado!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                playerTwoScoreCount++;
                playerScore();
                Toast.makeText(this,"Jugador 2 Ha ganado!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(rountCount == 9){
            Toast.makeText(this,"Empate", Toast.LENGTH_SHORT).show();
            playAgain();
        }else {
            player = !player;
        }
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerScore();
            }
        });
    }

    public boolean checkWinner(){
        boolean winner = false;

        for(int [] winPos : positions){
            if(gameState[winPos[0]] == gameState[winPos[1]] && gameState[winPos[1]] == gameState[winPos[2]] && gameState[winPos[0]] != 2){
                winner = true;
            }
        }
        return winner;

    }

    public void playerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));

    }

    public void playAgain(){
        rountCount = 0;
        player = true;

        for(int i = 0; i < buttons.length; i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
    }

    public void volver(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}