package net.nikonorov.translator.translator;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by vitaly on 04.10.15.
 */
public interface NetAPI {
    @GET("/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20150910T133746Z.5f37f78e06dd5d11.fcd0af38575fe88ec9a7b5c0921ff58d0fa007b4")
    void getDirections(Callback<Dirs> cb);
}
