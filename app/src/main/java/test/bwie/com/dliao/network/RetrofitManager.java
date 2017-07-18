package test.bwie.com.dliao.network;


import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import test.bwie.com.dliao.application.MyApplication;
import test.bwie.com.dliao.cipher.Md5Utils;
import test.bwie.com.dliao.core.JNICore;
import test.bwie.com.dliao.core.SortUtils;
import test.bwie.com.dliao.network.cookie.CookiesManager;

public class RetrofitManager {




    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookiesManager(MyApplication.myApplication))
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(20,TimeUnit.SECONDS)
            .addNetworkInterceptor(new LoggingInterceptor())
            .build();


    private   static ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://qhb.2dyt.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            //把 以前的 call 转化成 Observable
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ApiService.class);





    public  static void get(String url,Observer<String> observer){
         apiService.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }




    public static void get(String url, Map<String,String> map, Observer<String> observer){

         apiService.get(url,map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(observer);
    }





    public static void post(String url,Map<String,String> map, Observer<String> observer){

//        SortUtils.sortString(map)  mao key 排序
//        SortUtils.getMapResult(map)  map 拼接成字符串  返回值string
//        JNICore.getSign(string)   md5(appkey=11111&string)
//
     //String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
        String tsign =  SortUtils.getMapResult(SortUtils.sortString(map));
        String sign = Md5Utils.getMD5("appkey=1DLKlkdd&"+tsign);
        map.put("user.sign",sign);

          apiService.post(url,map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(observer);
    }


    public static void uploadPhoto(MultipartBody multipartBody,Map<String,String> map, Observer<String> observer){
        apiService.uploadPhoto(multipartBody,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }



}
