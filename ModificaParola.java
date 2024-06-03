package com.example.trigo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModificaParola extends AppCompatActivity {


    EditText pactuala, pnoua;
    Button modificareP;
    private MediaPlayer musicPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificaparola);

        pactuala=findViewById(R.id.pactuala);
        pnoua=findViewById(R.id.pnoua);
        modificareP = findViewById(R.id.modificaP);

        modificareP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayer != null) {
                    musicPlayer.release();
                }
                musicPlayer = MediaPlayer.create(ModificaParola.this, R.raw.click);
                musicPlayer.start();
                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                String parolaveche = pactuala.getText().toString();
                String parolanoua = pnoua.getText().toString();
                updateParola(parolaveche, parolanoua);
            }
        });
    }

    private void updateParola(String parolaveche, String parolanoua) {
        DBHelper dbHelper = new DBHelper(ModificaParola.this);
            if (dbHelper.updateParola(parolaveche, parolanoua)) {
                Toast.makeText(ModificaParola.this, "Parola a fost actualizată!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ModificaParola.this, "Actualizarea a eșuat!", Toast.LENGTH_LONG).show();
            }


    }
}
