package com.quanying.app.weburl;

import com.com.sky.downloader.greendao.ShopDao;
import com.quanying.app.app.MyApplication;
import com.quanying.app.bean.Shop;

import java.util.List;

public class LoveDao {

    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param shop
     */
    public static void insertLove(Shop shop) {
        MyApplication.getDaoInstant().getShopDao().insertOrReplace(shop);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteLove(long id) {
        MyApplication.getDaoInstant().getShopDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateLove(Shop shop) {
        MyApplication.getDaoInstant().getShopDao().update(shop);
    }

  /*
  * 查询 hx id 对应的信息
  * */
    public static List<Shop> queryLove(String hxid) {
        return MyApplication.getDaoInstant().getShopDao().queryBuilder().where(ShopDao.Properties.Type.eq(hxid)).list();
    }

    /**
     * 查询全部数据
     */
    public static List<Shop> queryAll() {
        return MyApplication.getDaoInstant().getShopDao().loadAll();
    }

}
