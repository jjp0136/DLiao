package test.bwie.com.dliao.core;

/**
 * Created by muhanxi on 17/7/4.
 */

public class JNICore {

    static {

        System.loadLibrary("core");

    }

    public static native String getSign(String sign);

}
