package com.example.jamechoi.madcamp3rd;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner sp_api;
    private RadioGroup rg_object_type;
    private RadioButton rb_json, rb_map;
    private Button bt_api_call;
    private TextView tv_data;

    private Context context;
    private String spinnerSelectedName;


    private ODsayService odsayService;
    private JSONObject jsonObject;
    private Map mapObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        context = this;
        sp_api = (Spinner) findViewById(R.id.sp_api);
        rg_object_type = (RadioGroup) findViewById(R.id.rg_object_type);
        bt_api_call = (Button) findViewById(R.id.bt_api_call);
        rb_json = (RadioButton) findViewById(R.id.rb_json);
        rb_map = (RadioButton) findViewById(R.id.rb_map);
        tv_data = (TextView) findViewById(R.id.tv_data);
        sp_api.setSelection(0);

        odsayService = ODsayService.init(MainActivity.this, getString(R.string.odsay_key));
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);

        bt_api_call.setOnClickListener(onClickListener);
        sp_api.setOnItemSelectedListener(onItemSelectedListener);
        rg_object_type.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (rg_object_type.getCheckedRadioButtonId() == rb_json.getId()) {
                tv_data.setText(jsonObject.toString());
            } else if (rg_object_type.getCheckedRadioButtonId() == rb_map.getId()) {
                tv_data.setText(mapObject.toString());
            }
        }
    };
    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            spinnerSelectedName = (String) parent.getItemAtPosition(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
            jsonObject = oDsayData.getJson();
            mapObject = oDsayData.getMap();
            if (rg_object_type.getCheckedRadioButtonId() == rb_json.getId()) {
                tv_data.setText(jsonObject.toString());
            } else if (rg_object_type.getCheckedRadioButtonId() == rb_map.getId()) {
                tv_data.setText(mapObject.toString());
            }
        }

        @Override
        public void onError(int i, String errorMessage, API api) {
            tv_data.setText("API : " + api.name() + "\n" + errorMessage);
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (spinnerSelectedName) {
                case "?????? ?????? ??????":
                    odsayService.requestSearchBusLane("150", "1000", "no", "10", "1", onResultCallbackListener);
                    break;
                case "???????????? ???????????? ??????":
                    odsayService.requestBusLaneDetail("12018", onResultCallbackListener);
                    break;
                case "??????????????? ???????????? ??????":
                    odsayService.requestBusStationInfo("107475", onResultCallbackListener);
                    break;
                case "?????????KTX ???????????? ??????":
                    odsayService.requestTrainServiceTime("3300128", "3300108", onResultCallbackListener);
                    break;
                case "???????????? ???????????? ??????":
                    odsayService.requestExpressServiceTime("4000057", "4000030", onResultCallbackListener);
                    break;
                case "???????????? ???????????? ??????":
                    odsayService.requestIntercityServiceTime("4000022", "4000255", onResultCallbackListener);
                    break;
                case "?????? ???????????? ??????":
                    odsayService.requestAirServiceTime("3500001", "3500003", "6", onResultCallbackListener);
                    break;
                case "??????????????? ???????????? ??????":
                    odsayService.requestSearchByCompany("792", "100", onResultCallbackListener);
                    break;
                case "???????????? ?????? ?????? ??????":
                    odsayService.requestSubwayStationInfo("130", onResultCallbackListener);
                    break;
                case "???????????? ?????? ????????? ??????":
                    odsayService.requestSubwayTimeTable("130", "1", "1", "1", onResultCallbackListener);
                    break;
                case "?????? ????????? ????????? ??????":
                    odsayService.requestLoadLane("0:0@12018:1:-1:-1", onResultCallbackListener);
                    break;
                case "???????????? ????????? ??????":
                    odsayService.requestSearchStation("11", "1000", "1:2", "10", "1", "127.0363583:37.5113295", onResultCallbackListener);
                    break;
                case "????????? ???????????? POI ??????":
                    odsayService.requestPointSearch("126.933361407195", "37.3643392278118", "250", "1:2", onResultCallbackListener);
                    break;
                case "?????? ??? ???????????? POI ??????":
                    odsayService.requestBoundarySearch("127.045478316811:37.68882830829:127.055063420699:37.6370465749586", "127.045478316811:37.68882830829:127.055063420699:37.6370465749586", "1:2", onResultCallbackListener);
                    break;
                case "????????? ???????????? ??????(????????? ?????????)":
                    odsayService.requestSubwayPath("1000", "201", "222", "2", onResultCallbackListener);
                    break;
                case "???????????? ?????????":
                    odsayService.requestSearchPubTransPath("126.926493082645", "37.6134436427887", "127.126936754911", "37.5004198786564", "0", "0", "0", onResultCallbackListener);
                    break;
                case "???????????? ?????? ?????? ??????":
                    odsayService.requestSubwayTransitInfo("133", onResultCallbackListener);
                    break;
                case "???????????? ????????? ??????":
                    odsayService.requestExpressBusTerminals("1000", "??????", onResultCallbackListener);
                    break;
                case "???????????? ????????? ??????":
                    odsayService.requestIntercityBusTerminals("1000", "??????", onResultCallbackListener);
                    break;
                case "???????????? ??????":
                    odsayService.requestSearchCID("??????", onResultCallbackListener);
                    break;
            }
        }
    };
}
