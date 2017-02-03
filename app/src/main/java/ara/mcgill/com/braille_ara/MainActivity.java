package ara.mcgill.com.braille_ara;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;

import net.mabboud.android_tone_player.ContinuousBuzzer;

import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.editText)
    EditText et;
    @BindView(R.id.editText1)
    EditText et1;
    @BindView(R.id.editText2)
    EditText et2;
    @BindView(R.id.editText3)
    EditText et3;
    @BindView(R.id.seekBar)
    SeekBar sb;
    @BindView(R.id.seekBar1)
    SeekBar sb1;
    @BindView(R.id.seekBar2)
    SeekBar sb2;
    @BindView(R.id.seekBar3)
    SeekBar sb3;

//    @BindString(R.string.login_error) String loginErrorMessage;

    boolean volume_changing = false;
    boolean paused = false;

    volatile EnhancedBuzzzer player = new EnhancedBuzzzer();
    volatile EnhancedBuzzzer player1 = new EnhancedBuzzzer();
    volatile EnhancedBuzzzer player2 = new EnhancedBuzzzer();
    volatile EnhancedBuzzzer player3 = new EnhancedBuzzzer();

    Thread worker = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (paused) continue;
//                try {
//                    Thread.currentThread().sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                player.play();
                player1.play();
                player2.play();
                player3.play();
            }

        }
    });

    @OnTextChanged (R.id.editText)
    void clickEt() {
        if (et.getText().length() <= 0) return;
        player.setToneFreqInHz(Integer.parseInt(et.getText().toString()));
    }

    @OnTextChanged (R.id.editText1)
    void clickEt1() {
        if (et1.getText().length() <= 0) return;
        player1.setToneFreqInHz(Integer.parseInt(et1.getText().toString()));
    }

    @OnTextChanged (R.id.editText2)
    void clickEt2() {
        if (et2.getText().length() <= 0) return;
        player2.setToneFreqInHz(Integer.parseInt(et2.getText().toString()));
    }

    @OnTextChanged (R.id.editText3)
    void clickEt3() {
        if (et3.getText().length() <= 0) return;
        player3.setToneFreqInHz(Integer.parseInt(et3.getText().toString()));
    }

    void clicksb() {
//        player.stop();
        player.setVolume(sb.getProgress());
//        player.play();
    }

    void clicksb1() {
//        player1.stop();
        player1.setVolume(sb1.getProgress());
//        player1.play();
    }

    void clicksb2() {
//        player2.stop();
        player2.setVolume(sb2.getProgress());
//        player2.play();
    }

    void clicksb3() {
//        player3.stop();
        player3.setVolume(sb3.getProgress());
//        player3.play();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                clicksb();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                volume_changing = true;
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                volume_changing = false;
//            }
//        });
//        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                clicksb1();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                volume_changing = true;
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                volume_changing = false;
//            }
//        });
//        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                clicksb2();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                volume_changing = true;
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                volume_changing = false;
//            }
//        });
//        sb3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                clicksb3();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                volume_changing = true;
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                volume_changing = false;
//            }
//        });
//        player.setPausePeriodSeconds(1);
//        player1.setPausePeriodSeconds(1);
//        player2.setPausePeriodSeconds(1);
//        player3.setPausePeriodSeconds(1);
//        player.setToneFreqInHz(2000);
//        player1.setToneFreqInHz(500);
//        player2.setToneFreqInHz(1000);
//        player3.setToneFreqInHz(1500);
//        player.setVolume(0);
//        player1.setVolume(0);
//        player2.setVolume(0);
//        player3.setVolume(0);
////        player.play();
////        player1.play();
////        player2.play();
////        player3.play();
//        worker.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
//        worker.suspend();
        paused = true;
        worker.interrupt();
//        try {
//            worker.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        player.stop();
        player1.stop();
        player2.stop();
        player3.stop();
        finish();
//        worker = null;
//        try {
//            worker.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        paused = false;
//        worker = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    if (paused) continue;
//                    try {
//                        Thread.currentThread().sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    player.play();
//                    player1.play();
//                    player2.play();
//                    player3.play();
//                }
//            }
//        });

//        if (worker.isInterrupted()) worker.run();

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                clicksb();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                volume_changing = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volume_changing = false;
            }
        });
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                clicksb1();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                volume_changing = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volume_changing = false;
            }
        });
        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                clicksb2();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                volume_changing = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volume_changing = false;
            }
        });
        sb3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                clicksb3();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                volume_changing = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volume_changing = false;
            }
        });
        player.setPausePeriodSeconds(1);
        player1.setPausePeriodSeconds(1);
        player2.setPausePeriodSeconds(1);
        player3.setPausePeriodSeconds(1);
        player.setToneFreqInHz(2000);
        player1.setToneFreqInHz(500);
        player2.setToneFreqInHz(1000);
        player3.setToneFreqInHz(1500);
        player.setVolume(0);
        player1.setVolume(0);
        player2.setVolume(0);
        player3.setVolume(0);
//        player.play();
//        player1.play();
//        player2.play();
//        player3.play();
        worker.start();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        paused = true;
        worker.interrupt();
//        try {
//            worker.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        worker = null;
        player.stop();
        player1.stop();
        player2.stop();
        player3.stop();
        finish();
        System.exit(0);
        this.finishAffinity();
//        try {
//            worker.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        worker.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        paused = false;
        if (worker != null) worker.interrupt();
        worker = null;
        player.stop();
        player1.stop();
        player2.stop();
        player3.stop();
//        worker.stop();
    }

    @Override
    public void onBackPressed() { //here I capture the event onBackPress
        super.onBackPressed();
        onStop(); //call onStop
    }
}
