package net.nikonorov.translator.translator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            initNavigationDrawer(toolbar);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        JSONArray langList = null;
        TextView tv = (TextView) findViewById(R.id.debugText);

        try {
            langList = loadLangList();
            tv.setText(langList.getString(0));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initNavigationDrawer(Toolbar toolbar){


        AccountHeader accountHeader = initAccountHeader();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new SecondaryDrawerItem()
                                .withIcon(R.drawable.ic_translate_black_36dp)
                                .withName(R.string.nav_menu_item1)
                                .withIdentifier(1)
                                .withSetSelected(false)
                        ,
                        new DividerDrawerItem(),

                        new PrimaryDrawerItem()
                                .withName(R.string.nav_menu_item2)
                                .withIcon(R.drawable.ic_settings_applications_black_36dp)
                                .withSetSelected(true)
                                .withIdentifier(2)
                        ,
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withName(R.string.nav_menu_item3)
                                .withIcon(R.drawable.ic_info_outline_black_36dp)
                                .withIdentifier(3)
                                .withSetSelected(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        Intent intent;
                        switch (iDrawerItem.getIdentifier()) {
                            case 1:
                                intent = new Intent(SettingsActivity.this, TranslationActivity.class);
                                startActivity(intent);
                                break;
                            case 3:
                                intent = new Intent(SettingsActivity.this, AboutUsActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                })
                .withAccountHeader(accountHeader)
                .build();
    }

    private AccountHeader initAccountHeader(){

        IProfile profile = new ProfileDrawerItem()
                .withEmail("email")
                .withName("Your Name")
                .withIcon(R.drawable.ic_verified_user_black_36dp);

        return new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.md_nd_background)
                .addProfiles(profile)
                .build();

    }

    private JSONArray loadLangList() throws ExecutionException, InterruptedException, JSONException {
            JSONObject output =
                    new NetWorker()
                            .execute("https://translate.yandex.net/api/v1.5/tr.json/getLangs?" +
                                    "key=trnsl.1.1.20150910T133746Z.5f37f78e06dd5d11.fcd0af38575fe88ec9a7b5c0921ff58d0fa007b4")
                            .get();

            JSONArray resArray = output.getJSONArray("dirs");
        return resArray;
    }

}
