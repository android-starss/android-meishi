package com.example.lihongzheng.meishi;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.example.chenxuhua.meishi.LoginActivity;
import com.example.chenxuhua.meishi.Person;
import com.example.hanxiaofeng.meishi.myInformation;
import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {

    int x = 0, y = 0, z;
  
    ImageSwitcher imageSwitcher;
    private int index=0; //图片序号
    private int[ ] images;//存放图片id
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 123) {
                imageSwitcher.setImageResource(images[msg.arg1]);
                imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_out_right));
//设置淡入和淡出动画（见前页，略）
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(getApplicationContext(),"6e4d5ffc19fe31b14ada255f23e5ae4d");
        setContentView(R.layout.activity_main);

        App app = (App) getApplication();
        final Person person=(Person)app.getPerson();

        ImageButton btn1 = findViewById(R.id.imageButton);
        ImageButton btn2=findViewById(R.id.gerenzhongxin);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() { //设置工厂
            @Override
            public View makeView() { //重写makeView
                ImageView imageView=new ImageView(MainActivity.this);
                imageView.setBackgroundColor(0xFFFFFFFF); //白色背景
                imageView.setScaleType(ImageView.ScaleType.CENTER); //居中显示
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT) ); //定义布局
                return imageView;
            }
        }); //见前页
        images = new int[]{
                R.drawable.luobotang,
                //R.drawable.chaomian,
                R.drawable.mutongfan

        };
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (index <= images.length) {
                            Message msg = new Message();
                            msg.what = 123;
                            msg.arg1 = index;
                            handler.sendMessage(msg);
                            index++;
                            if (index >= images.length) {
                                index = 0;
                            }
                            try {
                                Thread.sleep(1000); //暂停2秒继续，try…catch省略
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(person!=null){
                    Intent intent1=new Intent(MainActivity.this,myInformation.class);
                    startActivity(intent1);
                }
                else{
                    Intent intent2=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent2);
                }

            }
        });
    }
}
