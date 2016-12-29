package com.zhoujian.rxjava.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhoujian.rxjava.R;
import com.zhoujian.rxjava.bean.Actor;
import com.zhoujian.rxjava.bean.Movie;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //baseUse();
        //difineUse();
        //difineUseDemo();
        //schedulerDemo();
        //transForm();
        //transFormA();
        flatMap();
    }

    private void flatMap()
    {

        Actor actor1 = new Actor("周星驰", "男");
        Actor actor2 = new Actor("张柏芝", "女");
        ArrayList<Actor> movie1List = new ArrayList<Actor>();
        movie1List.add(actor1);
        movie1List.add(actor2);
        Movie movie1 = new Movie("1998-10-14", 1, movie1List, "喜剧之王");
        Actor actor3 = new Actor("罗志祥", "男");
        Actor actor4 = new Actor("张雨绮", "女");
        ArrayList<Actor> movie2List = new ArrayList<Actor>();
        movie2List.add(actor3);
        movie2List.add(actor4);
        Movie movie2 = new Movie("2016-05-01", 2, movie2List, "美人鱼");
        Movie[] movies = {movie1, movie2};

        //把一个电影装换成多个演员输出,并打印出演员的名字------flatMap

        Observer<Actor> observer = new Observer<Actor>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "Completed方法执行了");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError方法执行了");
            }
            @Override
            public void onNext(Actor actor) {
                Log.i(TAG, "onNext方法执行了");
                Log.i(TAG, "actor===" + actor.getName());
            }
        };

        Observable.from(movies).flatMap(new Func1<Movie, Observable<Actor>>() {
            @Override
            public Observable<Actor> call(Movie movie) {
                return Observable.from(movie.getMactorList());
            }
        }).subscribe(observer);



    }

    private void transFormA() {
        Actor actor1 = new Actor("周星驰", "男");
        Actor actor2 = new Actor("张柏芝", "女");
        ArrayList<Actor> movie1List = new ArrayList<Actor>();
        movie1List.add(actor1);
        movie1List.add(actor2);
        Movie movie1 = new Movie("1998-10-14", 1, movie1List, "喜剧之王");
        Actor actor3 = new Actor("罗志祥", "男");
        Actor actor4 = new Actor("张雨绮", "女");
        ArrayList<Actor> movie2List = new ArrayList<Actor>();
        movie2List.add(actor3);
        movie2List.add(actor4);
        Movie movie2 = new Movie("2016-05-01", 2, movie2List, "美人鱼");
        Movie[] movies = {movie1, movie2};
        //这是把对象装换成String 类型输出  -------map
        //首先，定义观察者
        Observer observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "Completed方法执行了");
            }
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError方法执行了");
            }
            @Override
            public void onNext(String s) {

                Log.i(TAG, "onNext方法执行了");
                Log.i(TAG,"s==="+s);
            }
        };
        //定义被观察者
        Observable observable=  Observable.from(movies).map(new Func1<Movie, String>() {
            @Override
            public String call(Movie movie) {
                return movie.toString();
            }
        });
        observable.subscribe(observer);

    }

    private void transForm()
    {

        Observable.just("100")
        .map(new Func1<String, Integer>()
        {
                @Override
                public Integer call(String s) {
                     return Integer.valueOf(s);
                }

        })
        .subscribe(new Action1<Integer>()
        {
                 @Override
                 public void call(Integer integer)
                 {
                     Log.d(TAG, "String转换成Integer完成了,integer的值为："+integer.intValue());
                 }
        });

    }

    private void schedulerDemo()
    {

        final ImageView img = (ImageView) findViewById(R.id.img);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(R.mipmap.my);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())//上面的call方法发生在io线程
          . observeOn(AndroidSchedulers.mainThread())//下面显示图片发生在主线程
          . subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted方法执行了");
            }
            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNext(Drawable drawable) {
                Log.d(TAG, "onNext方法执行了");
                img.setImageDrawable(drawable);
                Log.d(TAG, "图片加载完成了！");
            }
        });
    }

    private void difineUseDemo()
    {

        String[] names = {"周杰伦","周星驰","周润发"};
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "姓名:=="+s);
            }
        });
    }

    private void difineUse()
    {

        Observable observable = Observable.just("Hello","Rxjava","RxAndroid");
        //自定义回调
        Action1<String> nextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "自定义回调----onNext方法执行了: " + s);
            }
        };
        Action1<Throwable> errorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(TAG, "自定义回调----onError方法执行了!");
            }
        };
        Action0 completeAction = new Action0() {
            @Override
            public void call() {
                Log.i(TAG, "自定义回调----Completed方法执行了");
            }
        };
        //observable.subscribe(nextAction);
        //observable.subscribe(nextAction,errorAction);
        observable.subscribe(nextAction,errorAction,completeAction);

    }

    private void baseUse()
    {

        //观察者Observer
        Observer<String> observer = new Observer<String>()
        {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext方法执行了: " + s);
            }

            @Override
            public void onCompleted() {
                Log.i(TAG, "Completed方法执行了");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError方法执行了!");
            }
        };


       Subscriber<String> subscriber = new Subscriber<String>()
       {
            @Override
            public void onCompleted()
            {
                Log.i(TAG, "Completed方法执行了");
            }
            @Override
            public void onError(Throwable e)
            {
                Log.i(TAG, "onError方法执行了!");
            }
            @Override
            public void onNext(String s)
            {
                Log.i(TAG, "onNext方法执行了: " + s);
            }
        };

        //被观察者
       /* Observable observable = Observable.create(new Observable.OnSubscribe<String>()
        {
            @Override
            public void call(Subscriber<? super String> subscriber)
            {
                subscriber.onNext("Hello");
                subscriber.onNext("Rxjava");
                subscriber.onNext("RxAndroid");
                subscriber.onCompleted();
            }
        });
*/

       // Observable observable = Observable.just("Hello","Rxjava","RxAndroid");
        String[] strings = {"Hello","Rxjava","RxAndroid"};
        Observable observable = Observable.from(strings);
        //被观察者订阅观察者
        observable.subscribe(observer);
    }
}