package com.example.trigo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreareCont extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText varsta;
    AppCompatButton cont;
    private MediaPlayer musicPlayer;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.crearecont);

        username = (EditText) findViewById(R.id.inputname);
        password = (EditText) findViewById(R.id.inputpass);
        varsta = (EditText) findViewById(R.id.inputvarsta);
        cont = (AppCompatButton) findViewById(R.id.button);
        db = new DBHelper (this);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                int var = Integer.parseInt(varsta.getText().toString());
                if (musicPlayer != null) {
                    musicPlayer.release();
                }
                musicPlayer = MediaPlayer.create(CreareCont.this, R.raw.click);
                musicPlayer.start();
                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                if(user.equals("") || pass.equals("") || var==0)
                    Toast.makeText(CreareCont.this,"Câmpuri incomplete.", Toast.LENGTH_SHORT).show();
                else if( user.length() < 5 || pass.length() < 5 || var < 12){
                    Toast.makeText(CreareCont.this,"Parola și numele de utilizator trebuie să conțină cel puțin 5 caractere.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuser = db.checkusername(user);
                    if(checkuser == false){
                        Boolean insert = db.insertData(user,pass,var);
                        if(insert==true){
                            Toast.makeText(CreareCont.this, "Înregistrare cu succes.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Conectare.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(CreareCont.this, "Înregistrarea a eșuat.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(CreareCont.this, "Există deja acest utilizator.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
     }
}
