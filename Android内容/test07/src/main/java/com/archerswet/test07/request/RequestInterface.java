package com.archerswet.test07.request;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RequestInterface {

    //登录
    @POST("login")
    @FormUrlEncoded //Form 表单提交  UrlEncoded 上传链接进行编码 比如"动漫" => %E5%8A%A8%E6%BC%AB
    Call<ResponseBody> login(@Field("uid") String uid,@Field("upassword") String upassword,@Field("usite") Integer usite);

    //注册
    @POST("reg")
    @Multipart //如果要图文上传，就必须使用Multipart模式
    Call<ResponseBody> reg(@Part("uid") Integer uid, @Part("upassword") String upassword, @Part("uname") String uname, @Part MultipartBody.Part uimg);

    //修改个人信息 修改头像

    //查询所有书籍
    @GET("query")
    Call<ResponseBody> selectAll();

    //根据输入内容查询书籍
    @GET("query")
    Call<ResponseBody> selectByName(@Query("name") String name);

    //通过bid查询书籍
    @GET("query")
    Call<ResponseBody> selectById(@Query("id") Integer id);

    //借书
    @POST("lend/add")
    @FormUrlEncoded
    Call<ResponseBody> lend(@Field("uid") Integer uid,@Field("bid") Integer bid);

    //查询用户是否借过书
    @GET("lend/user/exist")
    Call<ResponseBody> exist(@Query("uid") Integer uid,@Query("bid") Integer bid);

    //用户查询自己的借书记录
    @GET("lend/user/query")
    Call<ResponseBody> selectLend(@Query("uid") Integer uid);

    //用户查询自己的借书记录
    @GET("lend/user/query")
    Call<ResponseBody> selectLend(@Query("uid") Integer uid,@Query("name") String name);

    //管理员查询用户
    @POST("admin/query/user")
    @FormUrlEncoded
    Call<ResponseBody> selectUser(@Field("uid") Integer uid);

    @POST("admin/query/user")
    @FormUrlEncoded
    Call<ResponseBody> selectUser(@Field("uname") String uname);

    @POST("admin/query/user")
    Call<ResponseBody> selectUser();
//https://movie.douban.com/tag/#/?sort=U&range=0,10&tags=%E5%8A%A8%E6%BC%AB

}
