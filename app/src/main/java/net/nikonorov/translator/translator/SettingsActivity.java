package net.nikonorov.translator.translator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class SettingsActivity extends BaseActivity {

    private List<Direction> directions;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initToolbar();

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
    }

    @Override
    protected void onResume() {
        super.onResume();


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://translate.yandex.net")
                .build();
        NetAPI service = restAdapter.create(NetAPI.class);


        service.getDirections(new Callback<Dirs>() {
            public void success(Dirs dirs, retrofit.client.Response arg1) {
                initializeData(dirs);
                initializeAdapter();
            }

            public void failure(RetrofitError arg0) {
            }
        });

        //JSONArray langList = null;
        /*try {
            langList = loadLangList();
            initializeData(langList);
            initializeAdapter();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    protected void initNavigationDrawer(Toolbar toolbar){

        AccountHeader accountHeader = initAccountHeader();

        Drawer result = super.prepareNavigationDrawer(toolbar)
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



    private JSONArray loadLangList() throws ExecutionException, InterruptedException, JSONException {

        JSONObject output =
                    new NetWorker()
                            .execute("https://translate.yandex.net/api/v1.5/tr.json/getLangs?" +
                                    "key=trnsl.1.1.20150910T133746Z.5f37f78e06dd5d11.fcd0af38575fe88ec9a7b5c0921ff58d0fa007b4")
                            .get();

            return output.getJSONArray("dirs");
    }


    private void initializeData(Dirs langList) {
        directions = new ArrayList<>();
        for (int j = 0; j < langList.dirs.length; j++) {
            directions.add(new Direction(langList.dirs[j], this));
        }
    }

    private void initializeAdapter(){
        RVSettingAdapter adapter = new RVSettingAdapter(directions, this);
        rv.setAdapter(adapter);
    }

}
