package com.leandom.simpleglidelayout;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.leandom.simpleglidelayout.utils.IOUtils;

/**
 * 模拟网络请求
 */

public class DataFactory {


    public static void getData(final Context context, final String fileName, final OnDataLoadListener<ImagesModel> listener) {

        AsyncTask<String, Void, ImagesModel> asyncTask = new AsyncTask<String, Void, ImagesModel>() {
            @Override
            protected ImagesModel doInBackground(String... params) {

                try {
                    Thread.sleep(1000);
                    String str = IOUtils.getStringFromAssets(context.getApplicationContext(), params[0]);
                    Gson gson = new Gson();
                    return gson.fromJson(str, ImagesModel.class);
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ImagesModel imagesModel) {

                if (imagesModel != null) {
                    listener.onSuccess(imagesModel);
                } else {
                    listener.onError();
                }
            }
        };

        asyncTask.execute(fileName);

    }


    public interface OnDataLoadListener<T> {

        public void onSuccess(T result);

        public void onError();
    }

}
