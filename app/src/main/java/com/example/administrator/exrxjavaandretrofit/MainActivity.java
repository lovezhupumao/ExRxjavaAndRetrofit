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
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private TextView mtext;
    private EditText meditview;
    private Button   mBtn;
    private Button mBtnSource;
    private Button mBtnDest;
    private int count=0;
    private String key="2f121eb8bf260e938df638ec3cc2e5d4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtext=(TextView)findViewById(R.id.mytext);
        meditview=(EditText)findViewById(R.id.myedit);
        mBtn=(Button)findViewById(R.id.btn);
        mBtnSource=(Button)findViewById(R.id.react_source);
        mBtnDest=(Button)findViewById(R.id.react_dest);
        mBtnSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
            }
        });
        Observable observable=Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(String.valueOf(count));
            }
        });

        RxView.clicks(mBtnSource)
                .skip(3).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mBtnDest.setText(count+"");
            }
        });

        retrofitAndRxjava();
        flatMap();

    }

    private void flatMap() {
        Button btn=(Button)findViewById(R.id.flatmapbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FlatMapModel model=new FlatMapModel(new Course("math","0"),"20103177","zpm");


              final Observable observable= Observable.create(new Observable.OnSubscribe<FlatMapModel>(){
                  @Override
                  public void call(Subscriber<? super FlatMapModel> subscriber) {
                      subscriber.onNext(model);
                  }
              });

                observable.flatMap(new Func1<FlatMapModel,Observable<Course>>(){
                    @Override
                    public Observable<Course> call(final FlatMapModel flatMapModel) {
                        return Observable.create(new Observable.OnSubscribe<Course>(){
                            @Override
                            public void call(Subscriber<? super Course> subscriber) {
                                subscriber.onNext(flatMapModel.getCourse());
                            }
                        });
                    }
                }).map(new Func1<Course,String>(){
                    @Override
                    public String call(Course course) {
                        return course.getClassName();
                    }
                }).subscribe(new Subscriber<String>(){
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("***********-----*",e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                    Log.i("--------------",s);
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void retrofitAndRxjava() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("http://apis.juhe.cn/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                final Phone phone=retrofit.create(Phone.class);
                phone.getHaoMa(meditview.getText().toString(), key)
                        .map(new Func1<PhoneNumInfo, String>() {
                            @Override
                            public String call(PhoneNumInfo phoneNumInfo) {
                                return phoneNumInfo.getResult().getCity();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String s) {
                            mtext.setText(s);
                            }
                        });
                phone.getHaoMa(meditview.getText().toString(), key)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<PhoneNumInfo>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

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
