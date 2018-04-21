package com.lyx.dragbutton;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout mSelectLin;
    int minPrice, maxPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectLin=(LinearLayout) findViewById(R.id.lin_select);

        mSelectLin .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

    }




    private PopupWindow showPopupWindow() {

        LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View contentView = mLayoutInflater.inflate(R.layout.popuplayout_gv, null);


        final PriceIntervalView mPricePiv = (PriceIntervalView) contentView.findViewById(R.id.price_piv);
        TextView mDefineTv = (TextView) contentView.findViewById(R.id.tv_define);

        //        mPricePiv.computeScrollTo(30,40);


        final PopupWindow popWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setAnimationStyle(R.style.contextMenuAnim);
        popWindow.showAsDropDown(mSelectLin);


        mPricePiv.setOnpriceListener(new OnPriceListener() {
            @Override
            public void onUploadSuccess(int min, int max) {

                minPrice = min;
                maxPrice = max;
            }
        });


        mDefineTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
               Toast.makeText(MainActivity.this,minPrice+"-"+maxPrice+"万",Toast.LENGTH_LONG).show();


            }
        });




        View mOutSide = contentView.findViewById(R.id.outSideView);
        mOutSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//
            }
        });
        return popWindow;
    }



}
