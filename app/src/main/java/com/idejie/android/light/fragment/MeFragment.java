package com.idejie.android.light.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.idejie.android.light.R;
import com.idejie.android.light.widget.PullScrollView;


public class MeFragment extends Fragment implements PullScrollView.OnTurnListener {

private PullScrollView mScrollView;
    View meFragment;
    private TableLayout mMainLayout;
    private ImageView mHeadImg;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        meFragment=inflater.inflate(R.layout.fragment_me, container, false);
        context=meFragment.getContext();
        initView();
        showtable();
        return meFragment;
    }

    private void showtable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 30;
        layoutParams.bottomMargin = 10;
        layoutParams.topMargin = 10;

        for (int i = 0; i < 30; i++) {
            TableRow tableRow = new TableRow(context);
            TextView textView = new TextView(context);
            textView.setText("Ta推荐的好书： " + i);
            textView.setTextSize(20);
            textView.setPadding(15, 15, 15, 15);

            tableRow.addView(textView, layoutParams);
            if (i % 2 != 0) {
                tableRow.setBackgroundColor(Color.rgb(0,150,136));
            } else {
                tableRow.setBackgroundColor(Color.WHITE);
            }

            final int n = i;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Click item " + n, Toast.LENGTH_SHORT).show();
                }
            });

            mMainLayout.addView(tableRow);
        }
    }

    private void initView() {
        mScrollView = (PullScrollView) meFragment.findViewById(R.id.scrollView);
        mHeadImg = (ImageView) meFragment.findViewById(R.id.bg_img);

        mMainLayout= (TableLayout) meFragment.findViewById(R.id.table_layout);

        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
    }

    @Override
    public void onTurn() {

    }


}
