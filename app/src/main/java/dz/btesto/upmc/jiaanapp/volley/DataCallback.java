package dz.btesto.upmc.jiaanapp.volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by besto on 27/12/16.
 */

public interface DataCallback {

    void onSuccess(JSONObject result) throws JSONException;
}
