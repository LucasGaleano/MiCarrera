package com.example.lucasgaleano.micarrera.classes;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;


public class Ltextview extends AppCompatTextView {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Ltextview(Context context) {
        super(context);
        if(this.getId() == -1)
            this.setId(this.generateViewId());
    }


    @Override
    public String toString() {
        String entryName = null;
        entryName= String.valueOf(this.getId());
        return (entryName);
    }

}
