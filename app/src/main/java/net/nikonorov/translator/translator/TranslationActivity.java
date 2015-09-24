package net.nikonorov.translator.translator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class TranslationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            initNavigationDrawer(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_translation, menu);
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
                        new PrimaryDrawerItem()
                                .withIcon(R.drawable.ic_translate_black_36dp)
                                .withName(R.string.nav_menu_item1)
                                .withIdentifier(1)
                                .withSetSelected(true),
                        new DividerDrawerItem(),

                        new SecondaryDrawerItem()
                                .withName(R.string.nav_menu_item2)
                                .withIcon(R.drawable.ic_settings_applications_black_36dp)
                                .withIdentifier(2)
                                .withSetSelected(false)
                        ,
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
                            case 2:
                                intent = new Intent(TranslationActivity.this, SettingsActivity.class);
                                startActivity(intent);
                                break;
                            case 3:
                                intent = new Intent(TranslationActivity.this, AboutUsActivity.class);
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


    public void translateBtnClc(View view) throws ExecutionException, InterruptedException, JSONException {
        EditText textToTranslate = (EditText) findViewById(R.id.textToTranslate);
        String strToTranslate = null;
        strToTranslate = textToTranslate.getText().toString();
        if (strToTranslate != null) {
            JSONObject output =
                    new NetWorker()
                            .execute("https://translate.yandex.net/api/v1.5/tr.json/translate?" +
                                    "key=trnsl.1.1.20150910T133746Z.5f37f78e06dd5d11.fcd0af38575fe88ec9a7b5c0921ff58d0fa007b4" +
                                    "&text=" + strToTranslate +
                                    "&lang=en-ru" +
                                    "&format=plain")
                            .get();

            EditText translated = (EditText) findViewById(R.id.translated);

            JSONArray resArray = output.getJSONArray("text");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < resArray.length(); i++) {
                sb.append(resArray.getString(i));
                if (i != resArray.length() - 1) {
                    sb.append(" ");
                }
            }

            translated.setText(sb.toString());
        }
    }
}
