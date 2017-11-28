package com.strang6.tinkofffintechschool;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Strang6 on 28.11.2017.
 */

public class MainPresenter {
    private WeakReference<MainActivity> view;
    private List<News> newsList;
    private static final String TAG = "MyTag";

    public void bindView(MainActivity view) {
        Log.d(TAG, "bindView");
        this.view = new WeakReference<>(view);
        if (newsList != null)
            view.setNewsList(newsList);
    }

    public void unbindView() {
        Log.d(TAG, "unbindView");
        view = null;
    }

    public void loadNews() {
        Log.d(TAG, "loadNews");
        new LoadNewsTask().execute();
    }

    private void onNewsLoad(String news) {
        Log.d(TAG, "onNewsLoad");
        if (news == null) {
            view.get().loadingError();
        } else {
            Type type = new TypeToken<List<News>>() {
            }.getType();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(type, new NewsListDeserializer()).create();
            newsList = gson.fromJson(news, type);
            Collections.sort(newsList, new Comparator<News>() {
                @Override
                public int compare(News news1, News news2) {
                    return news2.getPublicationDate().compareTo(news1.getPublicationDate());
                }
            });
            if (view != null)
                view.get().setNewsList(newsList);
        }
    }

    private class LoadNewsTask extends AsyncTask<Void, Void, String> {
        private String url = "https://api.tinkoff.ru/v1/news";

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            String json = null;
            try {
                Response response = client.newCall(request).execute();
                json = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String news) {
            super.onPostExecute(news);
            onNewsLoad(news);
        }
    }
}
