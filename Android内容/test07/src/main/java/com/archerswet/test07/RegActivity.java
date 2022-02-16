package com.archerswet.test07;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.archerswet.test07.request.RequestInterface;
import com.archerswet.test07.request.RetrofitTool;
import com.archerswet.test07.tool.PathFromUri;

import java.io.File;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * @description:注册页面
 * @author:archerswet@163.com
 * @date:2021/12/17
 */
public class RegActivity extends AppCompatActivity {

    @BindView(R.id.reg_edit_uid)
    EditText edit_uid;

    @BindView(R.id.reg_edit_uname)
    EditText edit_uname;

    @BindView(R.id.reg_edit_upassword)
    EditText edit_upassword;

    @BindView(R.id.reg_tv_uimg)
    TextView tv_uimg;

    private String path;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            String result = bundle.getString("data");
//            控制台（Logcat）打印
            Log.d("data_",result);
//            退回到主页面
            RegActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        ButterKnife.bind(this);
    }

    //上传用户注册的信息
    @OnClick(R.id.reg_btn_reg)
    public void reg(){
        File file = new File(path);//获取文件对象
        //定义上传文件的类型 当前上传的类型是图片
        RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
        //将文件File对象转化成可以上传的文件对象MultipartBody.Part
        MultipartBody.Part uimg = MultipartBody.Part.createFormData("uimg", file.getName(), fileRQ);

        //获取需要传输的字段
        String uid = edit_uid.getText().toString();
        String upassword = edit_upassword.getText().toString();
        String uname = edit_uname.getText().toString();
        //网络请求
        Retrofit retrofit = RetrofitTool.getRetrofitInstance();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = requestInterface.reg(Integer.valueOf(uid),upassword,uname,uimg);
        RetrofitTool.getRetrofitResult(call,handler);
    }


    //从相册中取出一张图片
    @OnClick(R.id.reg_btn_uimg)
    public void selectImageFromGallery(){
        //启动到相册
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        this.startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            //uri是选择的图片的缓存路径，不能用于上传
            Uri uri = data.getData();
            path = PathFromUri.getRealPathFromUri(this,uri);
            //显示图片的路径
            tv_uimg.setText(path);
        }
    }
}
