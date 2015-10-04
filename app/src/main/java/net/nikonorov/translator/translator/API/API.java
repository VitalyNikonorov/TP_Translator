package net.nikonorov.translator.translator.API;


import retrofit.Call;
import retrofit.Response;
import retrofit.http.GET;
import retrofit.http.Query;


//
//new NetWorker()
//        .execute("https://translate.yandex.net/api/v1.5/tr.json/translate?" +
//        "key=trnsl.1.1.20150910T133746Z.5f37f78e06dd5d11.fcd0af38575fe88ec9a7b5c0921ff58d0fa007b4" +
//        "&text=" + strToTranslate +
//        "&lang="+ Settings.DIRECTION +
//        "&format=plain")
//        .get();

public interface API {
    @GET("translate")
    public Call<Text> getText(@Query("key") String key, @Query("text") String text, @Query("lang") String lang, @Query("format") String format);
    @GET("getLangs")
    public Call<Lang> getLangs(@Query("key") String key);

}
