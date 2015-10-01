package net.nikonorov.translator.translator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initToolbar();

        // находим список
        ListView lvMain = (ListView) findViewById(R.id.lvAU);

        String[] names = { "Vitaly", "Boris", "Ivan" };

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.about_us_item, names);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
        return true;
    }

    protected void initNavigationDrawer(Toolbar toolbar){

        AccountHeader accountHeader = initAccountHeader();

        Drawer result = super.prepareNavigationDrawer(toolbar).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                Intent intent;
                switch (iDrawerItem.getIdentifier()) {
                    case 1:
                        intent = new Intent(AboutUsActivity.this, TranslationActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(AboutUsActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        })
                .withAccountHeader(accountHeader)
                .build();
    }
}
