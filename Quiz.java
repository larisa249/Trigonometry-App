package com.example.trigo;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup answerGroup;
    private Button submitButton;

    private String[] questions = {"Ce reprezintă sinusul? "
                    , "Ce reprezintă cosinusul? "
                    , "Funcția tangentă este "
                    , "Funcția cotangentă este "
                    , "Cercul trigonomteric este cercul cu centru în originea "
                    , "Valoarea lui sin(0) este "
                    , "Valoarea lui tg(90) este "
                    , "Formula fundamentală a trigonometriei este "
                    , "Definiția standard a tangentei este "
                    , "Teorema sinusurilor este "
                    , "Cot(2x) este "
                    , "Teorema cosinusurilor este "
                    , "Definiția cosinusului într-un triunghi dreptunghic este "
                    , " tg(x+y) este "
                    , "Valoarea lui ctg(-t) este "
                                    };
    private String[][] options = {{"abscisa", "ordonata", "atât abscisa cât și ordonata"}
            , {"abscisa", "ordonata", "atât abscisa cât și ordonata"}
            , {"fără paritate", "impară", "pară"}
            , {"impară", "fără paritate", "pară"}
            , {"O(180,180)", "O(60,60)", "O(0,0)"}
            , {"1", "1/2", "0"}
            , {"nu există", "1", "0"}
            , {"sinx + siny=0", "sin^2 + cos^2 = 1", "-1<=sin x <= 1"}
            , {"sin(x)/cos(x)", "cos(x)/sin(x)", "1-sin(x)/cos(x)"}
            , {"a/sin(A)=b/sin(B)=c/sin(C)=2R", "a/sin(a)=b/sin(b)=c/sin(c)=2R", "a/sin(a)=b/sin(b)=c/sin(c)=R"}
            , {"cos(x)/sin(x)", "(cot^2(x)-1)/2cot(x)", "cot^2(x)/2cot(x)"}
            , {"c^2 = a^2 + b^2 -2abccos(C)", "c^2 = a^2 + b^2 -2abcos(C)", "c = a + b -2abcos(C)"}
            , {"latura alăturată/ipotenuză", "latura opusă/ipotenuză", "ipotenuză/latură alăturată"}
            , {"(tg(x) + tg(y))/(1+tg(x)tg(y))", "(tg(x) - tg(y))/(1+tg(x)tg(y))", "(tg(x) + tg(y))/(1-tg(x)tg(y))"}
            , {"t", "ctg(t)", "-ctg(t)"}



    };
    private int[] answers = {R.id.option_b
            , R.id.option_a
            , R.id.option_b
            , R.id.option_a
            , R.id.option_c
            , R.id.option_c
            , R.id.option_a
            , R.id.option_b
            , R.id.option_a
            , R.id.option_a
            , R.id.option_b
            , R.id.option_b
            , R.id.option_a
            , R.id.option_c
            , R.id.option_c
    };
    private int currentQuestionIndex = 0;
    private MediaPlayer musicPlayer;
    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String username = prefs.getString("username", "defaultUser");
        questionText = findViewById(R.id.question_text);
        answerGroup = findViewById(R.id.answer_group);
        submitButton = findViewById(R.id.submit_button);
        shuffleQuestions();
        loadQuestion();


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicPlayer != null) {
                    musicPlayer.release();
                }
                musicPlayer = MediaPlayer.create(Quiz.this, R.raw.click);
                musicPlayer.start();
                musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                int selectedId = answerGroup.getCheckedRadioButtonId();
                if (selectedId == answers[currentQuestionIndex]) {
                    score++;
                    Toast.makeText(Quiz.this, "Corect!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Quiz.this, "Greșit!", Toast.LENGTH_SHORT).show();
                }
                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    loadQuestion();
                } else {
                    Toast.makeText(Quiz.this, "Ai terminat quiz-ul!", Toast.LENGTH_LONG).show();
                    String userScoreKey = username + "_scores";
                    String scores = prefs.getString(userScoreKey, "");
                    scores += score + ";";
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(userScoreKey, scores);
                    editor.apply();
                    finish();
                }
            }
        });
    }

    private void loadQuestion() {
        TextView scoreText = findViewById(R.id.score_text);
        questionText.setText(questions[currentQuestionIndex]);

        ((RadioButton)answerGroup.findViewById(R.id.option_a)).setText(options[currentQuestionIndex][0]);
        ((RadioButton)answerGroup.findViewById(R.id.option_b)).setText(options[currentQuestionIndex][1]);
        ((RadioButton)answerGroup.findViewById(R.id.option_c)).setText(options[currentQuestionIndex][2]);
        answerGroup.clearCheck();
        scoreText.setText("Scor: " + score);
    }

    private void shuffleQuestions() {
        int index;
        String tempQuestion;
        String[] tempOptions;
        int tempAnswer;
        java.util.Random random = new java.util.Random();
        for (int i = questions.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            tempQuestion = questions[index];
            questions[index] = questions[i];
            questions[i] = tempQuestion;

            tempOptions = options[index];
            options[index] = options[i];
            options[i] = tempOptions;

            tempAnswer = answers[index];
            answers[index] = answers[i];
            answers[i] = tempAnswer;
        }
    }



}

