package ara.mcgill.com.braille_ara;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import net.mabboud.android_tone_player.ContinuousBuzzer;

/**
 * Created by sangmoonhwang on 2/2/17.
 */

public class EnhancedBuzzzer extends ContinuousBuzzer {
    public EnhancedBuzzzer() {
        super();
    }

    @Override
    public void play() {
        super.play();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void setVolume(int volume) {
        super.setVolume(volume);
        float gain = (float) (volume / 100.0);
        if (audioTrack == null) return;
        audioTrack.setStereoVolume(gain, gain);
//        audioTrack.play();
    }

    @Override
    public int getVolume() {
        return super.getVolume();
    }

    @Override
    public double getToneFreqInHz() {
        return super.getToneFreqInHz();
    }

    @Override
    public void setToneFreqInHz(double toneFreqInHz) {
        super.setToneFreqInHz(toneFreqInHz);
    }

    /**
     * The time the buzzer should pause for every cycle in milliseconds.
     */
    @Override
    public int getPauseTimeInMs() {
        return super.getPauseTimeInMs();
    }

    /**
     * The time the buzzer should pause for every cycle in milliseconds.
     *
     * @param pauseTimeInMs
     */
    @Override
    public void setPauseTimeInMs(int pauseTimeInMs) {
        super.setPauseTimeInMs(pauseTimeInMs);
    }

    /**
     * The time period between when a buzzer pause should occur in seconds.
     */
    @Override
    public double getPausePeriodSeconds() {
        return super.getPausePeriodSeconds();
    }

    /**
     * The time period between when a buzzer pause should occur in seconds.
     * IE pause the buzzer every X/pausePeriod seconds.
     *
     * @param pausePeriodSeconds
     */
    @Override
    public void setPausePeriodSeconds(double pausePeriodSeconds) {
        super.setPausePeriodSeconds(pausePeriodSeconds);
    }

    @Override
    protected void asyncPlayTrack(final double toneFreqInHz) {
        playerWorker = new Thread(new Runnable() {
            public void run() {
                while (isPlaying || true) {
                    // will pause every x seconds useful for determining when a certain amount
                    // of time has passed while whatever the buzzer is signaling is active
                    playTone(toneFreqInHz, pausePeriodSeconds);
                    try {
                        Thread.sleep(pauseTimeInMs);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        });

        playerWorker.start();
    }

    @Override
    protected void tryStopPlayer() {
//        super.tryStopPlayer();
        isPlaying = false;
        try {
            if (playerWorker != null)
                playerWorker.interrupt();

            // pause() appears to be more snappy in audio cutoff than stop()
            if (audioTrack != null) {
                try {
//                    audioTrack.pause();
//                    audioTrack.flush();
                    audioTrack.release();
                } catch (Exception e) {
                    e.printStackTrace();
                    ;
                }
            }
//            audioTrack = null;
        } catch (IllegalStateException e) {
            // Likely issue with concurrency, doesn't matter, since means it's stopped anyway
            // so we just eat the exception
        }
    }

    /**
     * below from http://stackoverflow.com/questions/2413426/playing-an-arbitrary-tone-with-android
     *
     * @param freqInHz
     * @param seconds
     */
    @Override
    protected void playTone(double freqInHz, double seconds) {
        int sampleRate = 8000;

        double dnumSamples = seconds * sampleRate;
        dnumSamples = Math.ceil(dnumSamples);
        int numSamples = (int) dnumSamples;
        double sample[] = new double[numSamples];
        byte soundData[] = new byte[2 * numSamples];

        // Fill the sample array
        for (int i = 0; i < numSamples; ++i)
            sample[i] = Math.sin(freqInHz * 2 * Math.PI * i / (sampleRate));

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalized.
        int idx = 0;
        int i = 0;

        // Amplitude ramp as a percent of sample count
        int ramp = numSamples / 20;

        // Ramp amplitude up (to avoid clicks)
        for (i = 0; i < ramp; ++i) {
            double dVal = sample[i];
            // Ramp up to maximum
            final short val = (short) ((dVal * 32767 * i / ramp));
            // in 16 bit wav PCM, first byte is the low order byte
            soundData[idx++] = (byte) (val & 0x00ff);
            soundData[idx++] = (byte) ((val & 0xff00) >>> 8);
        }


        // Max amplitude for most of the samples
        for (i = i; i < numSamples - ramp; ++i) {
            double dVal = sample[i];
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            soundData[idx++] = (byte) (val & 0x00ff);
            soundData[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        // Ramp amplitude down
        for (i = i; i < numSamples; ++i) {
            double dVal = sample[i];
            // Ramp down to zero
            final short val = (short) ((dVal * 32767 * (numSamples - i) / ramp));
            // in 16 bit wav PCM, first byte is the low order byte
            soundData[idx++] = (byte) (val & 0x00ff);
            soundData[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        playSound(sampleRate, soundData);
    }

    @Override
    protected void playSound(int sampleRate, byte[] soundData) {
        try {
            int bufferSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, bufferSize,
                    AudioTrack.MODE_STREAM);

            float gain = (float) (volume / 100.0);
            //noinspection deprecation
//            audioTrack.reloadStaticData();
            audioTrack.setStereoVolume(gain, gain);
//            audioTrack.setLoopPoints(0,soundData.length,-1);
//            audioTrack.setPlaybackHeadPosition(100);
//            audioTrack.write(soundData, 0, soundData.length);
            audioTrack.play();
            audioTrack.write(soundData, 0, soundData.length);
        } catch (Exception e) {
            Log.e("tone player", e.toString());
        }
        try {
            if (audioTrack != null)
                tryStopPlayer();
        } catch (Exception ex) {
            //
        }
    }
}
