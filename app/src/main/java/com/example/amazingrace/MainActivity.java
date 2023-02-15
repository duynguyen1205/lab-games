package com.example.amazingrace;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlayer1,mediaPlayer2,mediaPlayer3;
    TextView txtDiem;
    EditText txtDiemCuoc;
    ImageButton ibtnPlay;
    CheckBox cbOne, cbTwo, cbThree;
    SeekBar skOne, skTwo, skThree;

    Button buttonRe;
    int sodiem = 100,diemBiTru,count,diemHientai;
    private ArrayList<CheckBox> mChecks;
    private ArrayList<CheckBox> mSelectedChecks;
    private List<SeekBar> seekBarList;
    private List<SeekBar> winnerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChecks = new ArrayList<CheckBox>();
        mSelectedChecks = new ArrayList<CheckBox>();

        mediaPlayer1 = MediaPlayer.create(this, R.raw.soxo);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.tiengdongdung);
        mediaPlayer3 = MediaPlayer.create(this, R.raw.tiengdongdung_1);
        mediaPlayer = MediaPlayer.create(this, R.raw.blackjack);
        mediaPlayer.start();

        mapping();

        DisableSeekBar();
        txtDiem.setText(sodiem+"");
        for(CheckBox c : mChecks) {
            c.setOnClickListener(this);
        }
        CountDownTimer countDownTimer = new CountDownTimer(60000, 300) {
            @Override
            public void onTick(long millisUntilFinished) {
                int max = 7;
                Random random = new Random();
                int oneNum = random.nextInt(max);
                int secondNum = random.nextInt(max);
                int thirdNum = random.nextInt(max);
                skOne.setProgress(skOne.getProgress() + oneNum);
                skTwo.setProgress(skTwo.getProgress() + secondNum);
                skThree.setProgress(skThree.getProgress() + thirdNum );
                seekBarList = new ArrayList<SeekBar>();
                seekBarList.add(skOne);
                seekBarList.add(skTwo);
                seekBarList.add(skThree);

                if (mSelectedChecks.size() > 1) {
                    checkWinner(seekBarList,this);
                }else {
                    if (skOne.getProgress() >= skOne.getMax()){
                        this.cancel();
                        ibtnPlay.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "ONE WIN", Toast.LENGTH_SHORT).show();

                        if(cbOne.isChecked()) {
                            sodiem +=diemBiTru;
                            Toast.makeText(MainActivity.this, "Bạn đoán chính xác", Toast.LENGTH_SHORT).show();
                            onPause();
                            mediaPlayer3.start();
                        }else {
                            sodiem -=diemBiTru;
                            Toast.makeText(MainActivity.this, "Bạn đoán sai rồi", Toast.LENGTH_SHORT).show();
                            onPause();
                            mediaPlayer2.start();
                        }
                        txtDiem.setText(sodiem+"");
                        EnableCheckBox();
                    }

                    if (skTwo.getProgress() >= skTwo.getMax()){
                        this.cancel();
                        ibtnPlay.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "TWO WIN", Toast.LENGTH_SHORT).show();
                        if(cbTwo.isChecked()) {
                            sodiem +=diemBiTru;
                            onPause();
                            mediaPlayer3.start();
                            Toast.makeText(MainActivity.this, "Bạn đoán chính xác", Toast.LENGTH_SHORT).show();
                        }else {
                            sodiem -=diemBiTru;
                            Toast.makeText(MainActivity.this, "Bạn đoán sai rồi", Toast.LENGTH_SHORT).show();
                            onPause();
                            mediaPlayer2.start();
                        }
                        txtDiem.setText(sodiem+"");
                        EnableCheckBox();
                    }

                    if (skThree.getProgress() >= skThree.getMax()){
                        this.cancel();
                        ibtnPlay.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "THREE WIN", Toast.LENGTH_SHORT).show();
                        if(cbThree.isChecked()) {
                            sodiem +=diemBiTru;
                            onPause();
                            mediaPlayer3.start();
                            Toast.makeText(MainActivity.this, "Bạn đoán chính xác", Toast.LENGTH_SHORT).show();
                        }else {
                            sodiem -=diemBiTru;
                            onPause();
                            mediaPlayer2.start();
                            Toast.makeText(MainActivity.this, "Bạn đoán sai rồi", Toast.LENGTH_SHORT).show();
                        }
                        txtDiem.setText(sodiem+"");
                        EnableCheckBox();
                    }
                }

            }

            @Override
            public void onFinish() {

            }
        };

        ibtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winnerList = new ArrayList<>();
                try {
                    txtDiemCuoc = (EditText)  findViewById(R.id.textScoreBet);
                    diemBiTru = Integer.parseInt(txtDiemCuoc.getText().toString());

                    Log.d("diemBiTru", String.valueOf(diemBiTru));
                    if (diemBiTru > sodiem){
                        Toast.makeText(MainActivity.this, "Khong dat cuoc cao hon so diem hien co", Toast.LENGTH_SHORT).show();
                    }else {
                        if(cbOne.isChecked() || cbTwo.isChecked() || cbThree.isChecked()) {
                            ibtnPlay.setVisibility(View.INVISIBLE);
                            skOne.setProgress(0);
                            skTwo.setProgress(0);
                            skThree.setProgress(0);
                            countDownTimer.start();
                            DisableCheckBox();
                            // thêm cái onPause vô đây trước
                            onPause();
                            // chạy nhạc mới
                            mediaPlayer1.start();
                        } else {
                            Toast.makeText(MainActivity.this, "Please bet before play",Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch(NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Nhap tien cuoc", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDiem.setText("100");
                skOne.setProgress(0);
                skTwo.setProgress(0);
                skThree.setProgress(0);
                cbOne.setChecked(false);
                cbTwo.setChecked(false);
                cbThree.setChecked(false);
            }
        });

    }
    private void EnableCheckBox() {
        cbOne.setEnabled(true);
        cbTwo.setEnabled(true);
        cbThree.setEnabled(true);
    }
    private void DisableCheckBox() {
        cbOne.setEnabled(false);
        cbTwo.setEnabled(false);
        cbThree.setEnabled(false);
    }
    private void DisableSeekBar() {
        skOne.setEnabled(false);
        skTwo.setEnabled(false);
        skThree.setEnabled(false);
    }
    private void mapping(){
        txtDiem = (TextView)  findViewById(R.id.txtScore);
        buttonRe = (Button)  findViewById(R.id.button);
        ibtnPlay = (ImageButton) findViewById(R.id.btnPlay);
        cbOne = (CheckBox) findViewById(R.id.checkbox1);
        cbTwo = (CheckBox) findViewById(R.id.checkbox2);
        cbThree = (CheckBox) findViewById(R.id.checkbox3);
        skOne = (SeekBar)  findViewById(R.id.seekbar1);
        skTwo = (SeekBar)  findViewById(R.id.seekbar2);
        skThree = (SeekBar)  findViewById(R.id.seekbar3);
        mChecks.add(cbOne);
        mChecks.add(cbTwo);
        mChecks.add(cbThree);
    }
    @Override
    public void onClick(View view) {
        CheckBox c = (CheckBox)view;

        if(mSelectedChecks.contains(c)) {
            mSelectedChecks.remove(c);
        } else {
            if(mSelectedChecks.size() < 2) {
                mSelectedChecks.add(c);
            } else {
                mSelectedChecks.remove(0);
                mSelectedChecks.add(c);
            }
        }

        drawResults();
    }
    public void drawResults() {
        for(CheckBox c : mChecks) {
            c.setChecked(mSelectedChecks.contains(c));
        }
    }

    private boolean checkExistedList(SeekBar skl) {
        boolean result = false;
        if (!winnerList.isEmpty()) {
            for (SeekBar sk : winnerList) {
                if (sk.equals(skl)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
    private void checkWinner(List<SeekBar> sks, CountDownTimer countDownTimer){
        count = 0;
        for (SeekBar sk :sks){
            if (sk.getProgress() >= sk.getMax()) {
                if (winnerList.isEmpty()){
                    winnerList.add(sk);
                }else {
                    if(!checkExistedList(sk)) {
                        winnerList.add(sk);
                    }
                }
            }
        }
        if (winnerList.size() == 3){
            Log.d("Winner3", winnerList.toString());
           winnerList.remove(1);
            Log.d("Winner3", winnerList.toString());
        }
                diemHientai = sodiem;
            if (!winnerList.isEmpty()){
                if (winnerList.size() == 2){
                    Log.d("Winner", winnerList.toString());
                    countDownTimer.cancel();
                    ibtnPlay.setVisibility(View.VISIBLE);

                    if (cbOne.isChecked()) {
                        if (winnerList.get(0).equals(skOne)){
                            Toast.makeText(MainActivity.this, "ONE WIN", Toast.LENGTH_SHORT).show();
                            sodiem +=diemBiTru;
                        }else if (winnerList.get(1).equals(skOne)){
                            Toast.makeText(MainActivity.this, "ONE WIN SECOND", Toast.LENGTH_SHORT).show();
                            sodiem +=( diemBiTru / 2);
                        }else {
                            Toast.makeText(MainActivity.this, "ONE Lose", Toast.LENGTH_SHORT).show();
                            sodiem -= diemBiTru;
                        }
                    }
                    if (cbTwo.isChecked()) {
                        if (winnerList.get(0).equals(skTwo)){
                            Toast.makeText(MainActivity.this, "TWO WIN", Toast.LENGTH_SHORT).show();
                            sodiem +=diemBiTru;
                        }else if (winnerList.get(1).equals(skTwo)){
                            Toast.makeText(MainActivity.this, "TWO WIN SECOND", Toast.LENGTH_SHORT).show();
                            sodiem +=( diemBiTru / 2);
                        }else {
                            Toast.makeText(MainActivity.this, "TWO Lose", Toast.LENGTH_SHORT).show();
                            sodiem -= diemBiTru;
                        }
                    }
                    if (cbThree.isChecked()) {
                        if (winnerList.get(0).equals(skThree)){
                            Toast.makeText(MainActivity.this, "THREE WIN", Toast.LENGTH_SHORT).show();
                            sodiem +=diemBiTru;
                        }else if (winnerList.get(1).equals(skThree)){
                            Toast.makeText(MainActivity.this, "THREE WIN SECOND", Toast.LENGTH_SHORT).show();
                            sodiem +=( diemBiTru / 2);
                        }else {
                            Toast.makeText(MainActivity.this, "THREE Lose", Toast.LENGTH_SHORT).show();
                            sodiem -= diemBiTru;
                        }
                    }
                    Log.d("diemHientai",String.valueOf(diemHientai));
                    Log.d("sodiem",String.valueOf(sodiem));
                    if(sodiem > diemHientai) {
                        onPause();
                        Log.d("sodiem1",String.valueOf(sodiem));
                        mediaPlayer3.start();
                    }else if(sodiem < diemHientai){
                        Log.d("sodiem2",String.valueOf(sodiem));
                        onPause();
                        mediaPlayer2.start();
                    }else {
                        onPause();
                        mediaPlayer2.start();
                    }
                }

            }

            txtDiem.setText(sodiem+"");
            EnableCheckBox();
            txtDiem.setFocusableInTouchMode(true);

    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        if(mediaPlayer1.isPlaying()) {
            mediaPlayer1.pause();
        }
    }

}