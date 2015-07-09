package ua.com.designmetis.audiotrackaudiorecord;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

public class AudioRec {

    String TAG = "myLogs";

    // создаем параметры записи звука
    int samplRate = 8000;
    int chanelConfigIn = AudioFormat.CHANNEL_IN_MONO;
    int chanelConfigOut = AudioFormat.CHANNEL_OUT_MONO;
    int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    int audioStream = AudioManager.STREAM_MUSIC;
    int audioMode = AudioTrack.MODE_STREAM;

    // размер буфера записи
    int bufferSize = 50 * AudioTrack.getMinBufferSize(samplRate, chanelConfigOut, audioFormat);

    // создаем объект AudioRecord для записи звука
    public AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, samplRate, chanelConfigIn, audioFormat, bufferSize);

    // создаем объект AudioTrack для воспроизведения звука (???)
    public AudioTrack audioTrack = new AudioTrack(audioStream, samplRate, chanelConfigOut, audioFormat, bufferSize, audioMode);


    // создаем буфер для записи звука
    short[] buffer = new short[bufferSize];

    // функция записи звука
    public  void record(){
        // начинаем запись
        audioRecord.startRecording();
        int recordState = audioRecord.getState();
        Log.d(TAG, "recordState " + recordState);
        // читаем звук в буфер
        audioRecord.read(buffer, 0, bufferSize);
        // останавливаем запись
        audioRecord.stop();
    }

    // функция воспроизведения звука
    public void play(){
        int i = 0;
        while (i < bufferSize){
            audioTrack.write(buffer, i++, bufferSize);
        }
        return;
    }




}
