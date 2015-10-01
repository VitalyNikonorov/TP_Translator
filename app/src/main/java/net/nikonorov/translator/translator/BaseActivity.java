package net.nikonorov.translator.translator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    protected abstract void initNavigationDrawer(Toolbar toolbar);

    protected void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            initNavigationDrawer(toolbar);
        }
    }

    protected AccountHeader initAccountHeader() {
        IProfile profile = new ProfileDrawerItem()
                .withEmail("2015")
                .withName("rm - rf /")
                .withIcon(R.drawable.ic_verified_user_black_36dp);

        return new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.md_nd_background)
                .addProfiles(profile)
                .build();
    }

    public DrawerBuilder prepareNavigationDrawer(Toolbar toolbar){

        return new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.nav_menu_item1)
                                .withIcon(R.drawable.ic_translate_black_36dp)
                                .withIdentifier(1)
                                .withSetSelected(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withName(R.string.nav_menu_item2)
                                .withIcon(R.drawable.ic_settings_applications_black_36dp)
                                .withIdentifier(2)
                                .withSetSelected(false),
                        new SecondaryDrawerItem()
                                .withName(R.string.nav_menu_item3)
                                .withIcon(R.drawable.ic_info_outline_black_36dp)
                                .withIdentifier(3)
                                .withSetSelected(false)
                );
    }
}