package com.example.chenxuhua.meishi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lihongzheng.meishi.App;
import com.example.lihongzheng.meishi.MainActivity;
import com.example.lihongzheng.meishi.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");
        Button btn1=(Button)findViewById(R.id.bt);
        Button btn2=(Button)findViewById(R.id.goregister);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=((EditText)findViewById(R.id.username)).getText().toString();
                final String psd = ((EditText) findViewById(R.id.psd)).getText().toString();
                BmobQuery<Person> query = new BmobQuery<>();
                //查询条件
                query.addWhereEqualTo("username", username);
                query.findObjects(new FindListener<Person>() {
                    @Override
                    public void done(List<Person> list, BmobException e) {
                        for (int i = 0; i < list.size(); i++) {


                            Person person=list.get(i);
                            Toast.makeText(getApplicationContext(),person.getName(),Toast.LENGTH_SHORT).show();
                           if(person.getPsd().equals(psd)){
                                Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                App app = (App) getApplication();
                                app.setPerson(person);
                            }

                            else {
                                Toast.makeText(getApplicationContext(), "密码错误，请输入正确的密码！", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }


                });
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goregister();
            }
        });
    }

    private void Goregister(){
        Intent intent=new Intent(LoginActivity.this,CreateuserActivity.class);
        startActivity(intent);
        finish();
    }
}
