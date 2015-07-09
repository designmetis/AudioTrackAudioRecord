package ua.com.designmetis.audiotrackaudiorecord;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

public class AudioRec {

    String TAG = "myLogs";


    int samplRate = 8000;
    int chanelConfigIn = AudioFormat.CHANNEL_IN_MONO;
    int chanelConfigOut = AudioFormat.CHANNEL_OUT_MONO;
    int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    int audioStream = AudioManager.STREAM_MUSIC;
    int audioMode = AudioTrack.MODE_STREAM;

    int bufferTrackSize = 50 * AudioTrack.getMinBufferSize(samplRate, chanelConfigOut, audioFormat);
    int bufferRecSize = 50*AudioRecord.getMinBufferSize(samplRate,chanelConfigIn,audioFormat);


    public AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, samplRate, chanelConfigIn, audioFormat, bufferTrackSize);

    public AudioTrack audioTrack = new AudioTrack(audioStream, samplRate, chanelConfigOut, audioFormat, bufferTrackSize, audioMode);


    short[] buffer = new short[bufferTrackSize];

    public  void record(){
        int initState = audioRecord.getState();
        Log.i(TAG, "BufferTracksize "+ bufferTrackSize+" BufferRecordSize "+ bufferRecSize);
        Log.d(TAG, "InitialState " + initState);
        audioRecord.startRecording();
        int recordingState = audioRecord.getRecordingState();
        Log.d(TAG, "RecordState " + recordingState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                audioRecord.read(buffer, 0, bufferTrackSize);
            }
        }).start();

    }


    public void play(){
        int offset = 0;
        int remaining = bufferTrackSize;
        Log.i(TAG, "AudioTrackState " + audioTrack.getState());
        if(audioTrack.getState() == AudioTrack.STATE_INITIALIZED) {
            while (0 < remaining) {
                int numWrite = audioTrack.write(buffer, offset, remaining);
                Log.i(TAG, "NumWrite " + numWrite);
                if (numWrite == AudioTrack.ERROR_INVALID_OPERATION
                        || numWrite == AudioTrack.ERROR_BAD_VALUE) {
                    throw new IllegalStateException("Huston, we have a problem. Not valid numWrite");
                }
                offset += numWrite;
                remaining -= numWrite;
            }
            audioTrack.play();
        }else throw new IllegalStateException("Huston, we have a problem. AudioTrack is not initialized");
    }
    public void stopRecord(){
        if(audioRecord.getState() == AudioRecord.STATE_INITIALIZED){
        audioRecord.stop();
        audioRecord.release();
        Log.i(TAG, "Get buff [1240]" + buffer[1240]);
            printBuffer();
        }
    }
    private void printBuffer(){
        StringBuilder sb = new StringBuilder(bufferTrackSize);
        for(int i = 0; i< buffer.length-1; i++){
            sb.append(buffer[i]);
            sb.append(" ");
        }
        Log.i(TAG, "Buffer contain "+ sb.toString());
    }






}
