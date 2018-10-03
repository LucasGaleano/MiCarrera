package com.example.lucasgaleano.micarrera.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.lucasgaleano.micarrera.R;
import com.example.lucasgaleano.micarrera.activities.CalendarActivity;
import com.example.lucasgaleano.micarrera.activities.TreeActivity;

public class NavigationMenu extends AppCompatActivity{

    private Context context;
    private DrawerLayout drawer;


    public NavigationMenu(Context context,DrawerLayout drawer){
        setContext(context);
        setDrawer(drawer);
    }

    public NavigationView.OnNavigationItemSelectedListener getListener(){

        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_materias && getContext().getClass() != TreeActivity.class) {
                    Intent intent = new Intent(getContext(),TreeActivity.class);
                    getContext().startActivity(intent);
                } else if (id == R.id.nav_Tareas) {

                } else if (id == R.id.nav_calendario && getContext().getClass() != CalendarActivity.class) {
                    Intent intent = new Intent(getContext(),CalendarActivity.class);
                    getContext().startActivity(intent);
                }

                getDrawer().closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }


    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }
}
