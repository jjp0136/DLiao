package test.bwie.com.dliao.application;

import android.app.Application;

import com.mob.MobApplication;

import test.bwie.com.dliao.dao.DaoMaster;
import test.bwie.com.dliao.dao.DaoSession;


/**
 * Created by lenovo-pc on 2017/7/5.
 */

public class MyApplication extends MobApplication {

    public static MyApplication myApplication;
    public static DaoSession daosession;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        DaoMaster.OpenHelper mydb= new DaoMaster.DevOpenHelper(this,"guy.db");
        DaoMaster master=new DaoMaster(mydb.getEncryptedWritableDb("000"));
        daosession = master.newSession();
    }

    public static MyApplication getApplication() {
        if (myApplication == null) {
            myApplication = getApplication();
        }
        return myApplication;
    }
}
