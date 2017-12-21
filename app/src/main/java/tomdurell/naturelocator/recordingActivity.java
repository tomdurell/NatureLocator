package tomdurell.naturelocator;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class recordingActivity extends AppCompatActivity {
    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;


    private  Button stopRecord;
    private  Button playbackButton;
    private  Button startRecord;
    private static String audioFilePath;
    private boolean recordingNow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        //setup buttons by id
        stopRecord = (Button) findViewById(R.id.stopRecord);
        playbackButton = (Button) findViewById(R.id.playbackButton);
        startRecord = (Button) findViewById(R.id.startRecord);
        //enable record button as no recording is occuring
        playbackButton.setEnabled(false);
        //disable as no recording is occuring
        stopRecord.setEnabled(false);
        //setup file fath, change this to randomised at a later date for no errors surrounding
        audioFilePath = "/storage/emulated/0/Android/data/natureRecording.3gp";
    }

    public void startRecording (View view) throws IOException
    {
        recordingNow = true;
        stopRecord.setEnabled(true);
        playbackButton.setEnabled(false);
        startRecord.setEnabled(false);
        //setup mediarecorder for recording microphone audio
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
        Toast.makeText(recordingActivity.this, "Recording Started!", Toast.LENGTH_SHORT).show();
    }
    public void stopRecording (View view)
    {

        stopRecord.setEnabled(false);
        playbackButton.setEnabled(true);

        if (recordingNow)
        {
            startRecord.setEnabled(true);
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            recordingNow = false;
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
            startRecord.setEnabled(true);
        }
        Toast.makeText(recordingActivity.this, "Recording Saved!", Toast.LENGTH_SHORT).show();
    }
    public void playbackRecording (View view) throws IOException
    {
        //currently not working, possible file path issue?
        playbackButton.setEnabled(false);
        startRecord.setEnabled(false);
        stopRecord.setEnabled(true);
        //setup mediaplayer with same file path for playback
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioFilePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }
}
