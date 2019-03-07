package com.quanying.app.weburl;
public class WebUri {



    public static String USERAGAENT = "qyw2018_android_20190305";


    private static String HOMEURL = "http://api.7192.com/";
    public static String LOGIN = HOMEURL+"index.php?m=api&mod=token";   //登录
    public static String VERSIONCODEUPDATE = HOMEURL+"index.php?m=api&mod=token&c=update";   //登录
    public static String GETUSERMSG = HOMEURL+"index.php?m=api&mod=info";   //获取用户信息
    public static String GETPHONECODE = HOMEURL+"index.php?m=api&mod=reg&c=regmsg";   //获取验证码
    public static String REGIST = HOMEURL+"index.php?m=api&mod=reg&c=save";   //注册
    public static String GETUSERTAG = HOMEURL+"index.php?m=api&mod=reg&c=labs";   //获取用户标签
    public static String UPDATAUSERMSG = HOMEURL+"index.php?m=api&mod=info&c=saveinfo";   //修改用户信息
    public static String UPDATAUSERHEAD = HOMEURL+"index.php?m=api&mod=upload&c=face";   //修改头像
    public static String UPDATAUSERPAGEBG = HOMEURL+"index.php?m=api&mod=upload&c=upbg";   //修改用户主页背景图
    public static String GETLATESTSHOWROOM = HOMEURL+"index.php?m=api&mod=hall&c=index";   //获取展厅最新列表
    public static String GETFOCUSROOM = HOMEURL+"index.php?m=api&mod=hall&c=care";   //获取展厅关注列表
    public static String GETOURCITYROOM = HOMEURL+"index.php?m=api&mod=hall&c=city";   //获取展厅同城列表
    public static String HOMELIST = HOMEURL+"index.php?m=api&mod=photo&c=cases";   //企业作品
    public static String UPDATAQYIMG = HOMEURL+"index.php?m=api&mod=upload&c=compic";   //上传企业作品
    public static String HOMEGRLIST = HOMEURL+"index.php?m=api&mod=photo&c=photos";   //个人作品
    public static String ZAN = HOMEURL+"index.php?m=api&mod=page&c=zan";   //点赞
    public static String SHOWROOMDETAILS = HOMEURL+"index.php?m=api&mod=photo&c=show";   //作品详情页
    public static String COMMENTSLIST = HOMEURL+"index.php?m=api&mod=page&c=notelist";   //评论列表
    public static String PL = HOMEURL+"index.php?m=api&mod=page&c=note";   //评论
    public static String USERPAGEHEAD = HOMEURL+"index.php?m=api&mod=my&c=index";   //用户主页上部分
    public static String USERPAGEIMG = HOMEURL+"index.php?m=api&mod=my&c=photo";   //用户作品
    public static String DELQYIMG = HOMEURL+"index.php?m=api&mod=my&c=delpic";   //用户作品
    public static String USERPAGEDEL = HOMEURL+"index.php?m=api&mod=photo&c=del";   //用户作品删除
    public static String PUSHCREATIONIMG = HOMEURL+"index.php?m=api&mod=upload&c=upphoto";   //用户作品上传
    public static String PUSHCREATIONSAVE = HOMEURL+"index.php?m=api&mod=photo&c=save";   //用户作品上传
    public static String MYCOLLECTION = HOMEURL+"index.php?m=api&mod=page&c=favlist";   //作品收藏列表
    public static String DELCOLLECTION = HOMEURL+"index.php?m=api&mod=page&c=unfav";   //删除作品收藏列表信息
    public static String SHOUCANG = HOMEURL+"index.php?m=api&mod=page&c=fav";   //删除作品收藏列表信息
    public static String NEWS = HOMEURL+"index.php?m=api&mod=page&c=qynews";   //官方消息接口
    public static String MYFANS = HOMEURL+"index.php?m=api&mod=my&c=fans";   //我的粉丝
    public static String ADDFOCUS = HOMEURL+"index.php?m=api&mod=my&c=careu";   //添加关注
    public static String MYFOCUS = HOMEURL+"index.php?m=api&mod=my&c=care";   //关注列表
    public static String DELFOCUS = HOMEURL+"index.php?m=api&mod=my&c=uncare";   //取消关注
    public static String BINDPHONE = HOMEURL+"index.php?m=api&mod=info&c=bdmsg";   //绑定手机号
    public static String BINDPHONEMSG = HOMEURL+"index.php?m=api&mod=info&c=savemobile";   //绑定手机号
    public static String FORGETPSWGETCODE = HOMEURL+"index.php?m=api&mod=reg&c=getcode";   //忘记密码界面获取验证码
    public static String FORGETPSWPUSH = HOMEURL+"index.php?m=api&mod=reg&c=resetpwd";   //忘记密码信息提交
    public static String HOTSEARCH = HOMEURL+"index.php?m=api&mod=info&c=seahot";   //热门搜索
    public static String SEARCHEND = HOMEURL+"index.php?m=api&mod=info&c=search";   //首页搜索结果
    public static String GETPASSFOREMS = "http://m.7192.com/getpwd/appgetajax";   //邮箱找回密码
    public static String WXLOGIN = HOMEURL+"index.php?m=api&mod=reg&c=loginwx";   //微信登录调用接口
    public static String QQLOGIN = HOMEURL+"index.php?m=api&mod=reg&c=loginqq";   //微信登录调用接口
    public static String SENDCODEFORTX =HOMEURL+ "index.php?m=api&mod=reg&c=sendwq";   //腾讯绑定手机号获取验证码
    public static String BINDTXPHONE =HOMEURL+ "index.php?m=api&mod=reg&c=bindwq";   //腾讯绑定手机号获取验证码
    public static String BUYVIPLIST =HOMEURL+ "index.php?m=api&mod=product";   //购买vip的列表
    public static String GETZPHD =HOMEURL+ "index.php?m=api&mod=page&c=notetips";   //获取作品互动列表
    public static String GETHXXX =HOMEURL+ "index.php?m=api&mod=info&c=hxname";   //通过环信账号获取用户信息
    public static String DOBUY = "http://m.7192.com/apporder.html";   //购买vip
    public static String GETMESSAGE = "http://api.7192.com/index.php?m=api&mod=info&c=hotnews";   //消息内容
}
