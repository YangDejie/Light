package com.idejie.android.light;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idejie.android.light.widget.SesameCreditPanel;
import com.idejie.android.light.widget.SesameItemModel;
import com.idejie.android.light.widget.SesameModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private SimpleDateFormat formater = new SimpleDateFormat("yyyy.MM.dd");
    private SesameCreditPanel panel;
    SesameModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        panel= (SesameCreditPanel) findViewById(R.id.panel);
        model= getData(300);
        model.setAssess("亮瞎眼！");
        panel.setDataModel(model);

    }
    private SesameModel getData(int i) {
        SesameModel model = new SesameModel();
        model.setTotalMin(0);
        model.setTotalMax(10000);
        model.setFirstText("测试版");
        model.setTopText("光线感应测试");
        model.setFourText("测量时间:" + formater.format(new Date()));
        ArrayList<SesameItemModel> sesameItemModels = new ArrayList<SesameItemModel>();

        SesameItemModel ItemModel350 = new SesameItemModel();
        ItemModel350.setArea("很暗");
        ItemModel350.setMin(0);
        ItemModel350.setMax(10);
        sesameItemModels.add(ItemModel350);

        SesameItemModel ItemModel550 = new SesameItemModel();
        ItemModel550.setArea("正常");
        ItemModel550.setMin(10);
        ItemModel550.setMax(100);
        sesameItemModels.add(ItemModel550);

        SesameItemModel ItemModel600 = new SesameItemModel();
        ItemModel600.setArea("较亮");
        ItemModel600.setMin(100);
        ItemModel600.setMax(2000);
        sesameItemModels.add(ItemModel600);

        SesameItemModel ItemModel650 = new SesameItemModel();
        ItemModel650.setArea("很亮");
        ItemModel650.setMin(2000);
        ItemModel650.setMax(5000);
        sesameItemModels.add(ItemModel650);

        SesameItemModel ItemModel700 = new SesameItemModel();
        ItemModel700.setArea("亮瞎眼");
        ItemModel700.setMin(5000);
        ItemModel700.setMax(10000);
        sesameItemModels.add(ItemModel700);

        model.setSesameItemModels(sesameItemModels);
        model.setUserTotal(i);
        return model;
    }
}
