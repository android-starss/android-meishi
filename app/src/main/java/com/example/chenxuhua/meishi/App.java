package com.example.chenxuhua.meishi;

import android.app.Application;

/**
 * Created by ASUS on 2018/7/11.
 */

public class App extends Application{
   private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
