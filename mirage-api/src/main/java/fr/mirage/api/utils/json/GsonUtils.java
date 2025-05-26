package fr.mirage.api.utils.json;

import com.google.gson.Gson;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GsonUtils {

    public Gson GSON = new Gson().newBuilder().create();

}