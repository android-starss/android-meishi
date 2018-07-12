package com.example.chenxuhua.meishi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lihongzheng.meishi.MainActivity;
import com.example.lihongzheng.meishi.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

public class CreateuserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createuser);
        setTitle("注册");
        final Person p2 = new Person();

        Button bt = (Button) findViewById(R.id.bt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = ((EditText) findViewById(R.id.username)).getText().toString();
                final String psd = ((EditText) findViewById(R.id.psd)).getText().toString();
                String sql = "Select *from Person";
                new BmobQuery<Person>().doSQLQuery(sql, new SQLQueryListener<Person>() {
                    @Override
                    public void done(BmobQueryResult<Person> bmobQueryResult, BmobException e) {
                        if (e == null) {
                            List<Person> list = (List<Person>) bmobQueryResult.getResults();
                            if (list != null && list.size() > 0) {
                                int m=1;
                                for (int i = 0; i < list.size(); i++) {
                                    Person person = list.get(i);
                                    if (person.getUsername() == username) {
                                        Toast.makeText(getApplicationContext(), "名称已被使用，请重新输入", Toast.LENGTH_SHORT).show();
                                        m=0;
                                    }

                                }
                                if(m==1) {
                                    p2.setUsername(username);
                                    p2.setPsd(psd);
                                    p2.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String objectId, BmobException e) {

                                            if (e == null) {
                                                Toast.makeText(getApplicationContext(), "注册成功，Id为：" + objectId, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(CreateuserActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "注册失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                }


                            }
                        }
                    }

                });


            }
        });
    }
}








