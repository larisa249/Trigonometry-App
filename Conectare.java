package com.example.trigo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Conectare extends AppCompatActivity {

    EditText username, password;
    Button con;
    DBHelper db;
    private MediaPlayer musicPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.conectare);

        username = (EditText) findViewById(R.id.inputname1);
        password = (EditText) findViewById(R.id.inputpass1);
        con = findViewById(R.id.button1);
        db = new DBHelper(this);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayer != null) {
                    musicPlayer.release();
                }
                musicPlayer = MediaPlayer.create(Conectare.this, R.raw.click);
                musicPlayer.start();
                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                String user = username.getText().toString();
                String pass = password.getText().toString();


                if(user.equals("") || pass.equals(""))
                    Toast.makeText(Conectare.this,"Câmpuri incomplete.", Toast.LENGTH_LONG).show();
                else{
                    Boolean checkuserpass = db.checkusernamepassword(user,pass);
                    if(checkuserpass == true){
                        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", user);
                        editor.apply();
                        Toast.makeText(Conectare.this, "Conectare reusită.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), Meniu.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(Conectare.this, "Credențiale incompatibile.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
