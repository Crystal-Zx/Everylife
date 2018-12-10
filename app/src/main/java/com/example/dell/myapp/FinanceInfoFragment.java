package com.example.dell.myapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class FinanceInfoFragment extends Fragment implements View.OnClickListener{

    private ImageButton toSina;
    private ImageButton toDongFang;
    private ImageButton toSouHu;
    private ImageButton toTencent;
    private ImageButton toWangYi;
    private ImageButton toStockStar;
    private ImageButton toHeXun;
    private ImageButton toCaiJing;
    private ImageButton toRenRen;
    private ImageButton toLanCai;
    private ImageButton toYiTong;
    private ImageButton toHuShen;

    private Uri content_url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.finance_info_fragment,container,false);
        toSina = view.findViewById(R.id.to_sina_info);
        toDongFang = view.findViewById(R.id.to_dongfang_info);
        toSouHu = view.findViewById(R.id.to_souhu_info);
        toTencent = view.findViewById(R.id.to_tencent_info);
        toWangYi = view.findViewById(R.id.to_wangyi_info);
        toStockStar = view.findViewById(R.id.to_stockstar_info);
        toHeXun = view.findViewById(R.id.to_hexun_info);
        toCaiJing = view.findViewById(R.id.to_caijing_info);
        toRenRen = view.findViewById(R.id.to_renren_info);
        toLanCai = view.findViewById(R.id.to_lancai_info);
        toYiTong = view.findViewById(R.id.to_yitong_info);
        toHuShen = view.findViewById(R.id.to_hushen_info);
        setListener();
        return view;
    }


    private void setListener() {
        toSina.setOnClickListener(this);
        toDongFang.setOnClickListener(this);
        toSouHu.setOnClickListener(this);
        toTencent.setOnClickListener(this);
        toWangYi.setOnClickListener(this);
        toStockStar.setOnClickListener(this);
        toHeXun.setOnClickListener(this);
        toCaiJing.setOnClickListener(this);
        toRenRen.setOnClickListener(this);
        toLanCai.setOnClickListener(this);
        toYiTong.setOnClickListener(this);
        toHuShen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_sina_info:
                content_url = Uri.parse("http://finance.sina.com.cn/");break;
            case R.id.to_dongfang_info:
                content_url = Uri.parse("http://www.eastmoney.com/");break;
            case R.id.to_souhu_info:
                content_url = Uri.parse("http://business.sohu.com/");break;
            case R.id.to_tencent_info:
                content_url = Uri.parse("http://finance.qq.com/");break;
            case R.id.to_wangyi_info:
                content_url = Uri.parse("http://money.163.com/");break;
            case R.id.to_stockstar_info:
                content_url = Uri.parse("http://www.stockstar.com/");break;
            case R.id.to_hexun_info:
                content_url = Uri.parse("http://www.hexun.com/");break;
            case R.id.to_caijing_info:
                content_url = Uri.parse("http://www.caijing.com.cn/");break;
            case R.id.to_renren_info:
                content_url = Uri.parse("https://www.renrendai.com/");break;
            case R.id.to_lancai_info:
                content_url = Uri.parse("https://www.ilancai.com/index.html?cid=7102");break;
            case R.id.to_yitong_info:
                content_url = Uri.parse("https://app.etongdai.com/static/pc-one/index.html?friendId=MTAyNjg1Mzg=&utm_source=sogoulogo");break;
            case R.id.to_hushen_info:
                content_url = Uri.parse("https://hushenlc.cn/jdEcard?toFrom=sougoudh001");break;
            default:break;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(content_url);
        startActivity(intent);
    }
}
