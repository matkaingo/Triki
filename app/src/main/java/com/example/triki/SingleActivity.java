package com.example.triki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class SingleActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView playerOneScore, playerTwoScore;
    private Button[] buttons = new Button[9];
    private Button reset;
    private Button menu;

    private int playerOneScoreCount, playerTwoScoreCount,rountCount,state;
    boolean player,restart;


    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] positions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //filas
            {0,3,6}, {1,4,7}, {2,5,8}, //columnas
            {0,4,8}, {2,4,6} //diagonales
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        playerOneScore = (TextView)findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView)findViewById(R.id.playerTwoScore);

        reset = (Button) findViewById(R.id.reset);

        for(int i=0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID,"id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        state = 0;
        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
    }

    @Override
    public void onClick(View v) {
        if (state == 0){
            if(!((Button)v).getText().toString().equals("")){
                return;
            }
            restart = false;
            player = true;
            String buttonID = v.getResources().getResourceEntryName(v.getId());
            int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
            rountCount++;
            state = CheckWinner();
            EndMatch();
            if (state==0 && rountCount < 9 && !restart){
                player = false;
                ia();
                rountCount++;
                state = CheckWinner();
                EndMatch();
            }
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

    public void EndMatch(){
        if (state == 1 || state == 2){
            if (state == 1){
                playerOneScoreCount++;
                playerScore();
                Toast.makeText(this,"Jugador 1 Ha ganado!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else {
                playerTwoScoreCount++;
                playerScore();
                Toast.makeText(this,"Jugador 2 Ha ganado!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(state == 3){
            Toast.makeText(this,"Empate", Toast.LENGTH_SHORT).show();
            playAgain();
        }
    }

    public void ia(){
        int number = generarAleatorio();
        while (gameState[number] != 2){
            number = generarAleatorio();
        }
        String buttonID = "btn_" + number;
        int resourceID = getResources().getIdentifier(buttonID,"id", getPackageName());
        Button SelectedButton = (Button)findViewById(resourceID);
        SelectedButton.setText("O");
        SelectedButton.setTextColor(Color.parseColor("#70FFEA"));
        gameState[number] = 1;
    }

    public int CheckWinner(){
        int WhoWins = 0;
        boolean winner = false;
        for(int [] winPos : positions){
            if(gameState[winPos[0]] == gameState[winPos[1]] && gameState[winPos[1]] == gameState[winPos[2]] && gameState[winPos[0]] != 2){
                winner = true;
            }
        }
        if (winner){
            if (player){
                WhoWins = 1;
            }else{
                WhoWins = 2;
            }
        }else{
            if (rountCount == 9){
                WhoWins = 3;
            }
        }
        return WhoWins;
    }

    public void playerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));

    }

    public void playAgain(){
        rountCount = 0;
        state = 0;
        player = true;
        restart = true;
        for(int i = 0; i < buttons.length; i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
    }

    public int generarAleatorio(){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        int numeroRandom = random.nextInt(9);
        return numeroRandom;
    }

    public void volver(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}