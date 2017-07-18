package test.bwie.com.dliao.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import test.bwie.com.dliao.R;
import test.bwie.com.dliao.activitys.LoginActivity;
import test.bwie.com.dliao.application.MyApplication;
import test.bwie.com.dliao.base.AppManager;
import test.bwie.com.dliao.base.IActivity;
import test.bwie.com.dliao.base.MyToast;
import test.bwie.com.dliao.beans.UploadPhotoBean;
import test.bwie.com.dliao.core.JNICore;
import test.bwie.com.dliao.core.SortUtils;
import test.bwie.com.dliao.network.BaseObserver;
import test.bwie.com.dliao.network.RetrofitManager;
import test.bwie.com.dliao.utils.Constants;
import test.bwie.com.dliao.utils.ImageResizeUtils;
import test.bwie.com.dliao.utils.SDCardUtils;

import static test.bwie.com.dliao.utils.ImageResizeUtils.copyStream;


/**
 * Created by lenovo-pc on 2017/7/10.
 */
@RuntimePermissions
public class RegistFragmentThread extends IActivity {
    @BindView(R.id.title_back)
    ImageButton titleBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.fragment_regist_photo_image)
    ImageView fragmentRegistPhotoImage;
    @BindView(R.id.fragment_regist_photo_btn_camera)
    Button fragmentRegistPhotoBtnCamera;
    @BindView(R.id.fragment_regist_photo_btn_photos)
    Button fragmentRegistPhotoBtnPhotos;
    @BindView(R.id.fragment_regist_photo_text_longin)
    TextView fragmentRegistPhotoTextLongin;

    private int width ;
    private int height ;
    private RegistFragmentThread activity;
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_regist_photo);
        ButterKnife.bind(this);
        activity=this;
        jumpLogin();
    }

    @OnClick({R.id.fragment_regist_photo_image, R.id.fragment_regist_photo_btn_camera, R.id.fragment_regist_photo_btn_photos, R.id.fragment_regist_photo_text_longin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_regist_photo_btn_camera:
                toCheckPermissionCamera();
                break;
            case R.id.fragment_regist_photo_btn_photos:
                toPhoto();
                break;
            case R.id.fragment_regist_photo_text_longin:
                jumpLogin();
                break;
            case R.id.title_back:
                activity.finish();
                break;

        }

//        RxView.clicks(view).throttleFirst(2, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
//                        KeyBoardCancle();
//                    }});

    }

    private void jumpLogin() {
        RxView.clicks(fragmentRegistPhotoTextLongin).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                        Intent intent = getIntent();
                        Bundle bundle = intent.getExtras();
                        activity.toIActicity(LoginActivity.class, bundle, 0);
                        activity.finish();
                    }
                });
    }

    static final int INTENTFORCAMERA = 1 ;
    static final int INTENTFORPHOTO = 2 ;

    public String LocalPhotoName;
    public String createLocalPhotoName() {
        LocalPhotoName = System.currentTimeMillis() + "face.jpg";
        return  LocalPhotoName ;
    }

    // TODO: 2017/7/11 开相机
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA} )
    public void toCamera(){
        try {
            Intent intentNow = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentNow.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(SDCardUtils.getMyFaceFile(createLocalPhotoName())));
            startActivityForResult(intentNow, INTENTFORCAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void showRationaleForCamera(final PermissionRequest request){

        new AlertDialog.Builder(this)
                .setMessage("需要打开您的相机来上传照片并保存照片")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        request.proceed();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }


    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void onDenied(){
        Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();

    }


    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void onNeverAsyAgain(){
        Toast.makeText(this, "不再提示", Toast.LENGTH_SHORT).show();
    }


    // TODO: 2017/7/11 开相册
    public void toPhoto(){
        try {
            createLocalPhotoName();
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType("image/*");
            startActivityForResult(getAlbum, INTENTFORPHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toCheckPermissionCamera(){
        RegistFragmentThreadPermissionsDispatcher.toCameraWithCheck(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RegistFragmentThreadPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case INTENTFORPHOTO:
                //相册
                try {
                    // 必须这样处理，不然在4.4.2手机上会出问题
                    Uri originalUri = data.getData();
                    File f = null;
                    if (originalUri != null) {
                        f = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor actualimagecursor =  this.getContentResolver().query(originalUri, proj, null, null, null);
                        if (null == actualimagecursor) {
                            if (originalUri.toString().startsWith("file:")) {
                                File file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                if(!file.exists()){
                                    //地址包含中文编码的地址做utf-8编码
                                    originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(),"UTF-8"));
                                    file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                }
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }
                        } else {
                            // 系统图库
                            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            actualimagecursor.moveToFirst();
                            String img_path = actualimagecursor.getString(actual_image_column_index);
                            if (img_path == null) {
                                InputStream inputStream = this.getContentResolver().openInputStream(originalUri);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            } else {
                                File file = new File(img_path);
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }
                        }
                        Bitmap bitmap = ImageResizeUtils.resizeImage(f.getAbsolutePath(), Constants.RESIZE_PIC);
                        width = bitmap.getWidth();
                        height = bitmap.getHeight();
                        FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
                        if (bitmap != null) {
                            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                                fragmentRegistPhotoImage.setImageBitmap(bitmap);
                                fos.close();
                                fos.flush();
                            }
                            if (!bitmap.isRecycled()) {
                                fragmentRegistPhotoImage.setImageBitmap(bitmap);
                                bitmap.isRecycled();
                            }
                            uploadFile(f);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case INTENTFORCAMERA:
//                相机
                try {
                    //file 就是拍照完 得到的原始照片
                    File file = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                    Bitmap bitmap = ImageResizeUtils.resizeImage(file.getAbsolutePath(), Constants.RESIZE_PIC);
                    width = bitmap.getWidth();
                    height = bitmap.getHeight();
                    FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                    if (bitmap != null) {
                        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                            fragmentRegistPhotoImage.setImageBitmap(bitmap);
                            fos.close();
                            fos.flush();
                        }
                        if (!bitmap.isRecycled()) {
                            fragmentRegistPhotoImage.setImageBitmap(bitmap);
                            //通知系统 回收bitmap
                            bitmap.isRecycled();
                        }
                        uploadFile(file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public void uploadFile(File file){
        if(!file.exists()){
            MyToast.makeText(this," 照片不存在",Toast.LENGTH_SHORT);
            return;
        }
        String [] arr = file.getAbsolutePath().split("/");

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        long ctimer = System.currentTimeMillis() ;
        Map<String,String> map = new HashMap<String,String>();
        map.put("user.currenttimer",ctimer+"");
        map.put("user.picWidth",width+"");
        map.put("user.picHeight",height+"");
        String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
        map.put("user.sign",sign);


        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("image",arr[arr.length-1],requestFile)
                .build();

//        key = value   addFormDataPart file 00101010110 key = value
//        key = value
//
//         file 00101010110
//        key = value


        RetrofitManager.uploadPhoto( body,map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                try {
                    Gson gson = new Gson();
                    UploadPhotoBean bean =  gson.fromJson(result, UploadPhotoBean.class);
                    if(bean.getResult_code() == 200){
                        MyToast.makeText(MyApplication.getApplication(),"上传成功",Toast.LENGTH_SHORT);
                    }
                    AppManager.getAppManager().finishActivity(RegistFragmentThread.class);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void onFailed(int code) {
                MyToast.makeText(MyApplication.getApplication(),""+code, Toast.LENGTH_SHORT);

            }
        });


    }
//    public void KeyBoardCancle() {
//        View view = getWindow().peekDecorView();
//        if (view != null) {
//            InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }
}
