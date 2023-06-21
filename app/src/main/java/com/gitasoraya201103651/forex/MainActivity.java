package com.gitasoraya201103651.forex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private ProgressBar loadingProgressBar;
    private SwipeRefreshLayout swipeRefreshLayout1;
    private TextView idrTextView, btcTextView, eurTextView, usdTextView, hkdTextView, bobTextView, audTextView, inrTextView, phpTextView, rubTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout1 = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout1);
        idrTextView = (TextView) findViewById(R.id.idrTextView);
        btcTextView = (TextView) findViewById(R.id.btcTextView);
        eurTextView = (TextView) findViewById(R.id.eurTextView);
        usdTextView = (TextView) findViewById(R.id.usdTextView);
        hkdTextView = (TextView) findViewById(R.id.hkdTextView);
        bobTextView = (TextView) findViewById(R.id.bobTextView);
        audTextView = (TextView) findViewById(R.id.audTextView);
        inrTextView = (TextView) findViewById(R.id.inrTextView);
        phpTextView = (TextView) findViewById(R.id.phpTextView);
        rubTextView = (TextView) findViewById(R.id.rubTextView);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        initSwipeRefreshLayout();
        initForex();
    }

    private void initSwipeRefreshLayout() {

        swipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initForex();

                swipeRefreshLayout1.setRefreshing(false);
            }
        });
    }

    public String formatNumber(double number, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(number);
    }

    private void initForex() {
        loadingProgressBar.setVisibility(TextView.VISIBLE);

        String url = "https://openexchangerates.org/api/latest.json?app_id=21e35a624deb44ec83921d5b830151ec";

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //log.d("*tw*", new String(responseBody));
                Gson gson = new Gson();
                RootModel rootModel = gson.fromJson(new String(responseBody), RootModel.class);
                RatesModel ratesModel = rootModel.getRatesModel();

                double idr = ratesModel.getIDR() / ratesModel.getIDR();
                double btc = ratesModel.getIDR() / ratesModel.getBTC();
                double eur = ratesModel.getIDR() / ratesModel.getEUR();
                double usd = ratesModel.getIDR() / ratesModel.getUSD();
                double hkd = ratesModel.getIDR() / ratesModel.getHKD();
                double bob = ratesModel.getIDR() / ratesModel.getBOB();
                double aud = ratesModel.getIDR() / ratesModel.getAUD();
                double inr = ratesModel.getIDR() / ratesModel.getINR();
                double php = ratesModel.getIDR() / ratesModel.getPHP();
                double rub = ratesModel.getIDR();

                idrTextView.setText(formatNumber(idr,"###,##0.##"));
                btcTextView.setText(formatNumber(btc,"###,##0.##"));
                eurTextView.setText(formatNumber(eur,"###,##0.##"));
                usdTextView.setText(formatNumber(usd,"###,##0.##"));
                hkdTextView.setText(formatNumber(hkd,"###,##0.##"));
                bobTextView.setText(formatNumber(bob,"###,##0.##"));
                audTextView.setText(formatNumber(aud,"###,##0.##"));
                inrTextView.setText(formatNumber(inr,"###,##0.##"));
                phpTextView.setText(formatNumber(php,"###,##0.##"));
                rubTextView.setText(formatNumber(rub,"###,##0.##"));

                loadingProgressBar.setVisibility(TextView.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                loadingProgressBar.setVisibility(TextView.INVISIBLE);
            }
        });
    }
}




