package net.nikonorov.translator.translator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TranslationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        initToolbar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_translation, menu);
        return true;
    }

    protected void initNavigationDrawer(Toolbar toolbar){

        AccountHeader accountHeader = initAccountHeader();

        Drawer result = super.prepareNavigationDrawer(toolbar).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
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

    public void translateBtnClc(View view) throws ExecutionException, InterruptedException, JSONException {
        EditText textToTranslate = (EditText) findViewById(R.id.textToTranslate);
        String strToTranslate = null;
        strToTranslate = textToTranslate.getText().toString();



        if (strToTranslate != null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://translate.yandex.net")
                    .build();

            NetAPI service = restAdapter.create(NetAPI.class);


            service.translate(strToTranslate, Settings.DIRECTION, new Callback<Translated>() {

                @Override
                public void success(Translated result, Response response) {
                    TextView translated = (TextView) findViewById(R.id.translated);

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < result.text.length; i++) {
                        sb.append(result.text[i]);
                        if (i != result.text.length - 1) {
                            sb.append(" ");
                        }
                    }

                    translated.setText(sb.toString());

                }

                public void failure(RetrofitError arg0) {
                }
            });


            /*
            JSONObject output =
                    new NetWorker()
                            .execute("https://translate.yandex.net/api/v1.5/tr.json/translate?" +
                                    "key=trnsl.1.1.20150910T133746Z.5f37f78e06dd5d11.fcd0af38575fe88ec9a7b5c0921ff58d0fa007b4" +
                                    "&text=" + strToTranslate +
                                    "&lang="+ Settings.DIRECTION +
                                    "&format=plain")
                            .get();
            */

        }
    }
}
