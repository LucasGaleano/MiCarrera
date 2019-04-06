package com.example.lucasgaleano.micarrera.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;


public class ItemListaView extends AppCompatTextView {
    Object item;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public ItemListaView(Context context) {
        super(context);
    }

    @Override
    public String toString() {
        String entryName = null;
        entryName= String.valueOf(this.getId());
        return (entryName);
    }

    public void additem(Object item){
        this.item=item;
        this.setText(item.toString());
    }

    public Object getItem (){
        return this.item;
    }

}
