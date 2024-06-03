package com.example.trigo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

import java.util.ArrayList;
import java.util.Arrays;

public class Calculator extends AppCompatActivity {
    EditText expressionInput;
    TextView result;
    SharedPreferences sharedPreferences;

    private DBHelper dbHelper;
    private MediaPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        dbHelper = new DBHelper(this);

        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);

        expressionInput = findViewById(R.id.edit_text_expression);
        result = findViewById(R.id.text_view_result);
        Button calculateButton = findViewById(R.id.button_calculate);
        Button help = findViewById(R.id.help);
        Button historyButton = findViewById(R.id.button_history);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayer != null) {
                    musicPlayer.release();
                }
                musicPlayer = MediaPlayer.create(Calculator.this, R.raw.click);
                musicPlayer.start();
                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Help();
            }
        });


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (musicPlayer != null) {
                        musicPlayer.release();
                    }
                    musicPlayer = MediaPlayer.create(Calculator.this, R.raw.click);
                    musicPlayer.start();
                    musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }
                    });

                    String input = expressionInput.getText().toString();
                    Expression expr = new ExpressionBuilder(input)
                            .functions(
                                    new Function("sin", 1) {
                                        @Override
                                        public double apply(double... args) {
                                            return Math.sin(Math.toRadians(args[0]));
                                        }
                                    },
                                    new Function("cos", 1) {
                                        @Override
                                        public double apply(double... args) {
                                            return Math.cos(Math.toRadians(args[0]));
                                        }
                                    },
                                    new Function("tan", 1) {
                                        @Override
                                        public double apply(double... args) {
                                            return Math.tan(Math.toRadians(args[0]));
                                        }
                                    },
                                    new Function("ctg", 1) {
                                        @Override
                                        public double apply(double... args) {
                                            return 1 / Math.tan(Math.toRadians(args[0]));
                                        }
                                    })
                            .variables("x", "e", "pi")
                            .build();

                    expr.setVariable("pi", Math.PI);
                    expr.setVariable("e", Math.E);

                    double resultValue = expr.evaluate();
                    result.setText(String.format("%.2f", resultValue));
                    saveExpression(input + " = " + result.getText().toString());
                } catch (Exception e) {
                    result.setText("Eroare: " + e.getMessage());
                }
            }
        });



        historyButton.setOnClickListener(v -> {
            if (musicPlayer != null) {
                musicPlayer.release();
            }
            musicPlayer = MediaPlayer.create(Calculator.this, R.raw.click);
            musicPlayer.start();
            musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            showHistory();
        });
}


    private String getCurrentUsername() {
        return sharedPreferences.getString("username", null);
    }
    private void saveExpression(String expression) {
        String username = getCurrentUsername();
        if (username != null) {
            dbHelper.insertExpression(username, expression);
        } else {
            Toast.makeText(this, "Niciun utilizator logat.", Toast.LENGTH_SHORT).show();
        }
    }
    private void showHistory() {
        String username = getCurrentUsername();
        if (username != null) {
            ArrayList<String> expressions = dbHelper.getUserHistory(username);
            if (!expressions.isEmpty()) {
                if (!isFinishing() && !isDestroyed()) {
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("Istoric")
                            .setMessage(TextUtils.join("\n", expressions))
                            .setPositiveButton("OK", null)
                            .create(); // Creează dialogul
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.alert_background);
                    dialog.show();
                }
            } else {
                Toast.makeText(this, "Nicio expresie salvată.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Niciun utilizator logat.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }


    public void Help() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "sin(x) = funcția sinus\n" +
                "cos(x) = funcția cosinus\n" +
                "tan(x) = funcția tangentă\n" +
                "ctg(x) = funcția cotangentă\n" +
                "+ adunare\n" +
                "- scădere\n" +
                "* înmulțire\n" +
                "* împărțire\n" +
                "pi = 3.14" );

        builder.setPositiveButton("Ok", null);
        builder.setTitle("Mod de utilizare");
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.alert_background);
        dialog.show();
    }


}
