package dz.btesto.upmc.jiaanapp.utils.volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * -------------------------
 * ### JI3AN APPLICATION ###
 * -------------------------
 * <p>
 * Created by :
 * ------------
 * ++ Nour Elislam SAIDI
 * ++ Mohamed Tayeb BENTERKI
 * <p>
 * ------ 2016-2017 --------
 */
public interface DataCallback {

    void onSuccess(JSONObject result) throws JSONException;

    void onSuccess(JSONArray result) throws JSONException;
}
