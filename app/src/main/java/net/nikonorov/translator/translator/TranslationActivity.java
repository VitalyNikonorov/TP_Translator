package net.nikonorov.translator.translator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import net.nikonorov.translator.translator.API.API;
import net.nikonorov.translator.translator.API.Text;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class TranslationActivity extends BaseActivity {
    EditText textToTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        initToolbar();
        textToTranslate = (EditText) findViewById(R.id.textToTranslate);
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

    public void translateBtnClc(View view) throws ExecutionException, InterruptedException, JSONException, IOException {

        String strToTranslate = null;
        strToTranslate = textToTranslate.getText().toString();

        hideSoftKeyboard();
        if (strToTranslate != null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            API myAPI = retrofit.create(API.class);

            Call<Text> call = myAPI.getText("trnsl.1.1.20150910T133746Z.5f37f78e06dd5d11.fcd0af38575fe88ec9a7b5c0921ff58d0fa007b4",
                    strToTranslate,
                    Settings.DIRECTION,
                    "plain");



            call.enqueue(new Callback<Text>() {
                @Override
                public void onResponse(Response<Text> response, Retrofit retrofit) {
                    Text text = response.body();
                    String[] result = text.text;

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < result.length; i++) {
                        sb.append(result[i]);
                        if (i != result.length - 1) {
                            sb.append(" ");
                        }
                    }
                    TextView translated = (TextView) findViewById(R.id.translated);
                    translated.setText(sb.toString());
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    private void hideSoftKeyboard(){
        if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textToTranslate.getWindowToken(), 0);
        }
    }

}
