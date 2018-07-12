package com.example.hanxiaofeng.meishi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lihongzheng.meishi.R;

public class myInformation extends Activity {

    Person person=new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        //用btnReset来监听重置个人信息事件
        Button btnReset=(Button)findViewById(R.id.reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(myInformation.this,myInformationReset.class);
                startActivity(intent);
            }
        });
/*
        //接收已经更改的个人信息
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        Person person= (Person) bundle.getSerializable("person"); //获取序列化对象(需强转)
        TextView name=(TextView)findViewById(R.id.name);
        name.setText(person.getName());*/
    }

}
