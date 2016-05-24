package com.example.administrator.exrxjavaandretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private TextView mtext;
    private EditText meditview;
    private Button   mBtn;
    private String key="2f121eb8bf260e938df638ec3cc2e5d4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtext=(TextView)findViewById(R.id.mytext);
        meditview=(EditText)findViewById(R.id.myedit);
        mBtn=(Button)findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("http://apis.juhe.cn/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                Phone phone=retrofit.create(Phone.class);
                phone.getHaoMa(meditview.getText().toString(), key)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<PhoneNumInfo>() {
                            @Override
                            public void onCompleted() {
                                Log.i("onCompleted", "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i("onError", e.getMessage());
                            }

                            @Override
                            public void onNext(PhoneNumInfo phoneNumInfo) {
                                mtext.setText(phoneNumInfo.getResult().getCity());
                            }
                        });


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
