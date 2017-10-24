package com.strang6.tinkofffintechschool;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Created by Strang6 on 24.10.2017.
 */

public class WorksLoader extends AsyncTaskLoader<String []> {
    private static String TAG = "myTag";

    private String [] myDataSet =
            {"Руслан и Людмила", "Кавказский пленник", "Гавриилиада",
                    "Полтава", "Медный всадник", "Евгений Онегин",
                    "Борис Годунов", "Моцарт и Сальери", "Арап Петра Великого", "Барышня-крестьянка",
                    "История Пугачёва", "Капитанская дочка", "Сказка о рыбаке и рыбке", "Пиковая дама",
                    "Дубровский", "Сказка о золотом петушке", "Станционный смотритель", "Метель"};

    public WorksLoader(Context context) {
        super(context);
        Log.d(TAG, "WorksLoader");
    }

    @Override
    public String[] loadInBackground() {
        Log.d(TAG, "loadInBackground");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myDataSet;
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d(TAG, "onStopLoading");
    }

    @Override
    protected boolean onCancelLoad() {
        Log.d(TAG, "onCancelLoad");
        return super.onCancelLoad();
    }

    @Override
    public void onCanceled(String[] data) {
        super.onCanceled(data);
        Log.d(TAG, "onCanceled");
    }
}
