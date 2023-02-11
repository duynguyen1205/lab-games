//package com.example.amazingrace;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.SeekBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//
//    TextView txtDiem;
//    EditText txtDiemCuoc;
//    ImageButton ibtnPlay;
//    CheckBox cbOne, cbTwo, cbThree;
//    SeekBar skOne, skTwo, skThree;
//
//    Button buttonRe;
//    int sodiem = 100,diemBiTru,count;
//    private ArrayList<CheckBox> mChecks;
//    private ArrayList<CheckBox> mSelectedChecks;
//    private List<SeekBar> seekBarList;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mChecks = new ArrayList<CheckBox>();
//        mSelectedChecks = new ArrayList<CheckBox>();
//        seekBarList = new ArrayList<SeekBar>();
//        mapping();
//
//        DisableSeekBar();
//        txtDiem.setText(sodiem+"");
//        for(CheckBox c : mChecks) {
//            c.setOnClickListener(this);
//        }
//        CountDownTimer countDownTimer = new CountDownTimer(60000, 300) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                int max = 7;
//                Random random = new Random();
//                int oneNum = random.nextInt(max);
//                int secondNum = random.nextInt(max);
//                int thirdNum = random.nextInt(max);
//                skOne.setProgress(skOne.getProgress() + oneNum + secondNum);
//                skTwo.setProgress(skTwo.getProgress() + secondNum + thirdNum + 2);
//                skThree.setProgress(skThree.getProgress() + thirdNum + oneNum + 2);
//                checkWinner(seekBarList,this);
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        };
//
//        ibtnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    txtDiemCuoc = (EditText)  findViewById(R.id.textScoreBet);
//                    diemBiTru = Integer.parseInt(txtDiemCuoc.getText().toString());
//                    Log.d("diemBiTru", String.valueOf(diemBiTru));
//                    if (diemBiTru > sodiem){
//                        Toast.makeText(MainActivity.this, "Khong dat cuoc cao hon so diem hien co", Toast.LENGTH_SHORT).show();
//                    }else {
//                        if(cbOne.isChecked() || cbTwo.isChecked() || cbThree.isChecked()) {
//                            ibtnPlay.setVisibility(View.INVISIBLE);
//                            skOne.setProgress(0);
//                            skTwo.setProgress(0);
//                            skThree.setProgress(0);
//                            countDownTimer.start();
//                            Log.d("alo","aoloolotaone");
//                            DisableCheckBox();
//                        } else {
//                            Toast.makeText(MainActivity.this, "Please bet before play",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }catch(NumberFormatException e){
//                    Toast.makeText(MainActivity.this, "Nhap tien cuoc", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        buttonRe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                txtDiem.setText("100");
//                skOne.setProgress(0);
//                skTwo.setProgress(0);
//                skThree.setProgress(0);
//                cbOne.setChecked(false);
//                cbTwo.setChecked(false);
//                cbThree.setChecked(false);
//            }
//        });
//
//    }
//    private void EnableCheckBox() {
//        cbOne.setEnabled(true);
//        cbTwo.setEnabled(true);
//        cbThree.setEnabled(true);
//    }
//    private void DisableCheckBox() {
//        cbOne.setEnabled(false);
//        cbTwo.setEnabled(false);
//        cbThree.setEnabled(false);
//    }
//    private void DisableSeekBar() {
//        skOne.setEnabled(false);
//        skTwo.setEnabled(false);
//        skThree.setEnabled(false);
//    }
//    private void mapping(){
//        txtDiem = (TextView)  findViewById(R.id.txtScore);
//        buttonRe = (Button)  findViewById(R.id.button);
//        ibtnPlay = (ImageButton) findViewById(R.id.btnPlay);
//        cbOne = (CheckBox) findViewById(R.id.checkbox1);
//        cbTwo = (CheckBox) findViewById(R.id.checkbox2);
//        cbThree = (CheckBox) findViewById(R.id.checkbox3);
//        skOne = (SeekBar)  findViewById(R.id.seekbar1);
//        skTwo = (SeekBar)  findViewById(R.id.seekbar2);
//        skThree = (SeekBar)  findViewById(R.id.seekbar3);
//        mChecks.add(cbOne);
//        mChecks.add(cbTwo);
//        mChecks.add(cbThree);
//        seekBarList.add(skOne);
//        seekBarList.add(skTwo);
//        seekBarList.add(skThree);
//    }
//    @Override
//    public void onClick(View view) {
//        CheckBox c = (CheckBox)view;
//
//        if(mSelectedChecks.contains(c)) {
//            mSelectedChecks.remove(c);
//        } else {
//            if(mSelectedChecks.size() < 2) {
//                mSelectedChecks.add(c);
//            } else {
//                mSelectedChecks.remove(0);
//                mSelectedChecks.add(c);
//            }
//        }
//
//        drawResults();
//    }
//    public void drawResults() {
//        for(CheckBox c : mChecks) {
//            c.setChecked(mSelectedChecks.contains(c));
//        }
//    }
//    private void checkWinner(List<SeekBar> sks, CountDownTimer countDownTimer){
//        count = 0;
//        List<SeekBar> winList = new ArrayList<>();
//        for (SeekBar sk :sks){
//            if (sk.getProgress() >= sk.getMax()) {
//                count++;
//                winList.add(sk);
//            }
//        }
//
//        if (count == 2) {
//            Log.d("so 1:",winList.get(0).toString());
//            Log.d("so 1:",winList.get(1).toString());
//            countDownTimer.cancel();
//            ibtnPlay.setVisibility(View.VISIBLE);
//            if (!winList.isEmpty()){
//                if (winList.size() == 2){
//                    if (cbOne.isChecked()) {
//                        if (winList.get(1).equals(skOne)){
//                            Toast.makeText(MainActivity.this, "ONE WIN", Toast.LENGTH_SHORT).show();
//                            sodiem +=diemBiTru;
//                        }else if (winList.get(0).equals(skOne)){
//                            Toast.makeText(MainActivity.this, "ONE WIN SECOND", Toast.LENGTH_SHORT).show();
//                            sodiem +=( diemBiTru / 2);
//                        }else {
//                            Toast.makeText(MainActivity.this, "ONE Lose", Toast.LENGTH_SHORT).show();
//                            sodiem -= diemBiTru;
//                        }
//                    }
//                    if (cbTwo.isChecked()) {
//                        if (winList.get(1).equals(skTwo)){
//                            Toast.makeText(MainActivity.this, "TWO WIN", Toast.LENGTH_SHORT).show();
//                            sodiem +=diemBiTru;
//                        }else if (winList.get(0).equals(skTwo)){
//                            Toast.makeText(MainActivity.this, "TWO WIN SECOND", Toast.LENGTH_SHORT).show();
//                            sodiem +=( diemBiTru / 2);
//                        }else {
//                            Toast.makeText(MainActivity.this, "TWO Lose", Toast.LENGTH_SHORT).show();
//                            sodiem -= diemBiTru;
//                        }
//                    }
//                    if (cbThree.isChecked()) {
//                        if (winList.get(1).equals(skThree)){
//                            Toast.makeText(MainActivity.this, "THREE WIN", Toast.LENGTH_SHORT).show();
//                            sodiem +=diemBiTru;
//                        }else if (winList.get(0).equals(skThree)){
//                            Toast.makeText(MainActivity.this, "THREE WIN SECOND", Toast.LENGTH_SHORT).show();
//                            sodiem +=( diemBiTru / 2);
//                        }else {
//                            Toast.makeText(MainActivity.this, "THREE Lose", Toast.LENGTH_SHORT).show();
//                            sodiem -= diemBiTru;
//                        }
//                    }
//                }
//            }
//            txtDiem.setText(sodiem+"");
//            EnableCheckBox();
//            txtDiem.setFocusableInTouchMode(true);
//        }
//    }
//}