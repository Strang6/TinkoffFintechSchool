package com.strang6.tinkofffintechschool;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Strang6 on 24.10.2017.
 */

public class WorksLoader extends AsyncTaskLoader<String []> {
    private String [] myDataSet =
            {"Руслан и Людмила", "Кавказский пленник", "Гавриилиада",
                    "Полтава", "Медный всадник", "Евгений Онегин",
                    "Борис Годунов", "Моцарт и Сальери", "Арап Петра Великого", "Барышня-крестьянка",
                    "История Пугачёва", "Капитанская дочка", "Сказка о рыбаке и рыбке", "Пиковая дама",
                    "Дубровский", "Сказка о золотом петушке", "Станционный смотритель", "Метель"};

    public WorksLoader(Context context) {
        super(context);
    }

    @Override
    public String[] loadInBackground() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myDataSet;
    }
}
