package com.example.trigo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModificaUsername extends AppCompatActivity {


    EditText uactual, unou;
    Button modificare;
    private MediaPlayer musicPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificausername);

        uactual=findViewById(R.id.uactual);
        unou = findViewById(R.id.unou);
        modificare = findViewById(R.id.modifica);

        modificare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayer != null) {
                    musicPlayer.release();
                }
                musicPlayer = MediaPlayer.create(ModificaUsername.this, R.raw.click);
                musicPlayer.start();
                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                String usernamevechi = uactual.getText().toString().trim();
                String usernamenou = unou.getText().toString().trim();
                if (!usernamevechi.isEmpty() && !usernamenou.isEmpty()) {
                    updateUsername(usernamevechi, usernamenou);
                } else {
                    Toast.makeText(ModificaUsername.this, "Completează ambele câmpuri!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void updateUsername(String usernamevechi, String usernamenou) {
        DBHelper dbHelper = new DBHelper(ModificaUsername.this);
        if (!dbHelper.checkusername(usernamenou)) {
            if (dbHelper.updateUsername(usernamevechi, usernamenou)) {
                Toast.makeText(ModificaUsername.this, "Numele de utilizator a fost actualizat!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ModificaUsername.this, "Actualizarea a eșuat!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(ModificaUsername.this, "Numele de utilizator este deja folosit!", Toast.LENGTH_LONG).show();
        }
    }
}