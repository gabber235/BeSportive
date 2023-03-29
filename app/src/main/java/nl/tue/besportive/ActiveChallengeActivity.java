package nl.tue.besportive;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import java.util.Timer;
import java.util.TimerTask;


import nl.tue.besportive.databinding.ActivityActiveChallengeBinding;

public class ActiveChallengeActivity extends AppCompatActivity {


    private ActivityActiveChallengeBinding binding;

    TextView timerText;
    Button start_button;

    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    TextView activeChallengedisplay;

    boolean timerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_challenge);

        timerText = (TextView) findViewById(R.id.timerText);
        start_button = (Button) findViewById(R.id.start_button);

        timer = new Timer();

        activeChallengedisplay = (TextView) findViewById(R.id.activeChallengedisplay);

        String name ="Challenge Not Set";
        Bundle extras =  getIntent().getExtras();

        if (extras != null){
            name = extras.getString("name");

        }
        activeChallengedisplay.setText(name);
    }

    public void resetTapped(View view)
    {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        resetAlert.setTitle("Cancel Challenge");
        resetAlert.setMessage("Are you sure you want to cancel the challenge?");
        resetAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(timerTask != null)
                {
                    timerTask.cancel();
                    setButtonUI("START", R.color.white);
                    time = 0.0;
                    timerStarted = false;
                    timerText.setText(formatTime(0,0,0));

                }
            }
        });

        resetAlert.setNeutralButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //do nothing
            }
        });

        resetAlert.show();

    }

    public void startStopTapped(View view)
    {
        if(timerStarted == false)
        {
            timerStarted = true;
            setButtonUI("STOP", R.color.white);

            startTimer();
        }
        else
        {
            timerStarted = false;
            setButtonUI("START", R.color.white);

            timerTask.cancel();
        }
    }

    private void setButtonUI(String start, int color)
    {
        start_button.setText(start);
        start_button.setTextColor(ContextCompat.getColor(this, color));
    }

    private void startTimer()
    {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time++;
                        timerText.setText(getTimerText());
                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);
    }


    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
    }


    private void leaderboard(View view) {
        startLeaderboardActivity();
    }

    private void challenges(View view) {
        startChallengesActivity();
    }

    private void startLeaderboardActivity() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void startChallengesActivity() {
        Intent intent = new Intent(this, ChallengesActivity.class);
        startActivity(intent);
        finish();
    }

}