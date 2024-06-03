package com.example.trigo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Setari extends AppCompatActivity {

    Button modificare, vizualizare, deconectare, stergere;
    private MediaPlayer musicPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setari);

        modificare = findViewById(R.id.m);
        vizualizare = findViewById(R.id.v);
        deconectare = findViewById(R.id.d);
        stergere = findViewById(R.id.s);

        modificare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayer != null) {
                    musicPlayer.release();
                }
                musicPlayer = MediaPlayer.create(Setari.this, R.raw.click);
                musicPlayer.start();
                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Intent intent = new Intent(getApplicationContext(), Modificare.class);
                startActivity(intent);
            }
        });

        deconectare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayer != null) {
                    musicPlayer.release();
                }
                musicPlayer = MediaPlayer.create(Setari.this, R.raw.click);
                musicPlayer.start();
                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Intent intent = new Intent(getApplicationContext(), Conectare.class);
                startActivity(intent);
            }
        });

        stergere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayer != null) {
                    musicPlayer.release();
                }
                musicPlayer = MediaPlayer.create(Setari.this, R.raw.click);
                musicPlayer.start();
                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", null);
                if (username != null) {
                    DBHelper dbHelper = new DBHelper(Setari.this);
                    boolean isDeleted = dbHelper.deleteUser(username);
                    if (isDeleted) {
                        Toast.makeText(Setari.this, "Contul a fost șters cu succes!", Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("username");
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Setari.this, "Ștergerea contului a eșuat.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Setari.this, "Eroare la identificarea utilizatorului.", Toast.LENGTH_LONG).show();
                }
            }
        });

    vizualizare.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (musicPlayer != null) {
                musicPlayer.release();
            }
            musicPlayer = MediaPlayer.create(Setari.this, R.raw.click);
            musicPlayer.start();
            musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            Intent intent = new Intent(getApplicationContext(), Progres.class);
            startActivity(intent);
        }
    });
    }
}