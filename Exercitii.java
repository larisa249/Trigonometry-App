package com.example.trigo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Exercitii extends AppCompatActivity {

    EditText latAB, latAC, latBC, unA,  unC;
    Button calculeaza;
    TextView rezultate;
    private MediaPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercitii);

        latAB = findViewById(R.id.AB);
        latAC = findViewById(R.id.CA);
        latBC = findViewById(R.id.BC);

        unA = findViewById(R.id.A);
        unC = findViewById(R.id.C);

        calculeaza = findViewById(R.id.v);
        rezultate = findViewById(R.id.text);

        calculeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayer != null) {
                    musicPlayer.release();
                }
                musicPlayer = MediaPlayer.create(Exercitii.this, R.raw.click);
                musicPlayer.start();
                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });

                calculeazaTriunghiul();


            }
        });
    }

        private double safeParseDouble(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Double.NaN;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    private void calculeazaTriunghiul() {
        double AB = safeParseDouble(latAB.getText().toString());
        double BC = safeParseDouble(latBC.getText().toString());
        double AC = safeParseDouble(latAC.getText().toString());
        double a = safeParseDouble(unA.getText().toString());
        double c = safeParseDouble(unC.getText().toString());

        double A = Math.toRadians(a);
        double C = Math.toRadians(c);

        if (!Double.isNaN(AB) && !Double.isNaN(BC) && !Double.isNaN(AC) && AB > 0 && BC > 0 && AC > 0) {
            cunoastemtoateLaturile(AB, BC, AC);
        } else if (!Double.isNaN(A) && A > 0 && A < 90 && !Double.isNaN(C) && C > 0 && C < 90) {
            if (!Double.isNaN(AB) && AB > 0) {
                cunoastemUnghiurileSiLatura(A, C, AB, "AB");
            } else if (!Double.isNaN(BC) && BC > 0) {
                cunoastemUnghiurileSiLatura(A,C, BC, "BC");
            } else if (!Double.isNaN(AC) && AC > 0) {
                cunoastemUnghiurileSiLatura(A,C, AC, "AC");
            }
        } else {
            rezultate.setText("Introduceți valori valide și pozitive pentru toate laturile.");
        }
    }

    private void cunoastemtoateLaturile(double AB, double BC, double AC) {

        if (AC * AC != (AB * AB + BC * BC)) {
            rezultate.setText("Laturile introduse nu respectă teorema lui Pitagora pentru un triunghi dreptunghic. Verificați valorile introduse.");
            return;
        }

        double angleA = Math.toDegrees(Math.asin(BC / AC));
        double angleC = Math.toDegrees(Math.asin(AB / AC));

        rezultate.setText("Știm lungimea tuturor laturilor, deci aplicând funcția sin pentru unghiurile A și C, iar apoi funcția arcsin, " +
                "aflăm cu ușurință măsura unghiurilor lipsă: \n" +
                "ex: sin(A) = BC/AC => A = arcsin(BC/AC)\n" +
                "Unghiul A: " + String.format("%.2f", angleA) + " grade\n" +
                "Unghiul C: " + String.format("%.2f", angleC) + " grade");
    }

    private void cunoastemUnghiurileSiLatura(double A, double C, double side, String sideType) {
        double AB, BC, AC, sinA, sinC;


        sinA = Math.sin(A);
        sinC = Math.sin(C);
        double b=90;

        if (sideType.equals("AB")) {
            AB = side;
            AC = ((Math.sin(b))*AB)/sinC;
            BC = (sinA*AB)/sinC;
        } else if (sideType.equals("BC")) {
            BC = side;
            AC = ((Math.sin(b))*BC)/sinA;
            AB =  (BC * sinC)/ sinA;
        } else { // AC
            AC = side;
            AB = (AC * sinC)/(Math.sin(b));
            BC = (AC * sinA)/(Math.sin(b));
        }

        rezultate.setText(String.format("Calculând cu teorema sinusurilor:\n" +
                "Lungimea laturii AB: %.2f \n" +
                "Lungimea laturii BC: %.2f \n" +
                "Lungimea laturii AC: %.2f ", AB, BC, AC));
    }


    public void showHelpDialog(View view) {
        if (musicPlayer != null) {
            musicPlayer.release();
        }
        musicPlayer = MediaPlayer.create(Exercitii.this, R.raw.click);
        musicPlayer.start();
        musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Unghiul B este unghiul dreptunghic al triunghiului => m(<B)=90.\n" +
                           "Suma măsurilor unghiurilor într-un triunghi dreptunghic este întotdeauna de 180 de grade.");

        builder.setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.alert_background);
        dialog.show();
    }


}

