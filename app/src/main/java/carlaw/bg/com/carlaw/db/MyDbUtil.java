package carlaw.bg.com.carlaw.db;

import android.content.Context;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import carlaw.bg.com.carlaw.app.L;


public class MyDbUtil {
    private static final java.lang.String TAG = "MyDbUtil";

    private static MyDbUtil myDbUtil;

    public static MyDbUtil getInstence(){
        if(myDbUtil == null ){
            myDbUtil = new MyDbUtil();
        }
        return myDbUtil;
    }

    private  DbManager db;

    public void init(String dbName) {
        /**
         * 初始化DaoConfig配置
         */
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                //设置数据库名，默认xutils.db
                .setDbName(dbName)
                //设置数据库路径，默认存储在app的私有目录
//                .setDbDir(new File("/mnt/sdcard/"))
                //设置数据库的版本号
                .setDbVersion(1)
                //设置数据库打开的监听
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        //开启数据库支持多线程操作，提升性能，对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                //设置数据库更新的监听
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    }
                })
                //设置表创建的监听
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table){
                        L.i(TAG, "onTableCreated：" + table.getName());
                    }
                });
        //设置是否允许事务，默认true
        //.setAllowTransaction(true)

         db = x.getDb(daoConfig);
    }
    //增加
    public void dbAdd() throws DbException {
        //User user = new User("Kevingo","caolbmail@gmail.com","13299999999",new Date());
        //db.save(user);//保存成功之后【不会】对user的主键进行赋值绑定
        //db.saveOrUpdate(user);//保存成功之后【会】对user的主键进行赋值绑定
        //db.saveBindingId(user);//保存成功之后【会】对user的主键进行赋值绑定,并返回保存是否成功

        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            //User的@Table注解onCreated属性加了name,email联合唯一索引.
            User user = new User("Kevingo" + System.currentTimeMillis()+i, "caolbmail@gmail.com", "13899999999", new Date());
            users.add(user);
        }
        db.saveBindingId(users);
        L.i("【dbAdd】第一个对象:" + users.get(0).toString());//user的主键Id不为0
    }
    //删除
    public void dbDelete() throws DbException {
        List<User> users = db.findAll(User.class);
        if(users == null || users.size() == 0){
            return;//请先调用dbAdd()方法
        }
        //db.delete(users.get(0)); //删除第一个对象
        //db.delete(User.class);//删除表中所有的User对象【慎用】
        //db.delete(users); //删除users对象集合
        //users =  db.findAll(User.class);
        // showDbMessage("【dbDelete#delete】数据库中还有user数目:" + users.size());

        WhereBuilder whereBuilder = WhereBuilder.b();
        whereBuilder.and("id",">","5").or("id","=","1").expr(" and mobile > '2015-12-29 00:00:01' ");
        db.delete(User.class,whereBuilder);
        users =  db.findAll(User.class);
        L.i("【dbDelete#delete】数据库中还有user数目:" + users.size());
    }
    //更新
    public void dbUpdate() throws DbException {
        List<User> users = db.findAll(User.class);
        if(users == null || users.size() == 0){
            return;//请先调用dbAdd()方法
        }
        User user = users.get(0);
        user.setEmail(System.currentTimeMillis() / 1000 + "@email.com");
        //db.replace(user);
        //db.update(user);
        //db.update(user,"email");//指定只对email列进行更新

        WhereBuilder whereBuilder = WhereBuilder.b();
        whereBuilder.and("id",">","5").or("id","=","1").expr(" and mobile > '2015-12-29 00:00:01' ");
        db.update(User.class,whereBuilder,
                new KeyValue("email",System.currentTimeMillis() / 1000 + "@email.com")
                ,new KeyValue("mobile","18988888888"));//对User表中复合whereBuilder所表达的条件的记录更新email和mobile
    }
    //查询
    public List<User> dbFind() throws DbException {
        //List<User> users = db.findAll(User.class);
        //showDbMessage("【dbFind#findAll】第一个对象:"+users.get(0).toString());

        //User user = db.findById(User.class, 1);
        //showDbMessage("【dbFind#findById】第一个对象:" + user.toString());

        //long count = db.selector(User.class).where("name","like","%kevin%").and("email","=","caolbmail@gmail.com").count();//返回复合条件的记录数
        //showDbMessage("【dbFind#selector】复合条件数目:" + count);

        List<User> users = db.selector(User.class)
                .where("name","like","%kevin%")
                .and("email", "=", "caolbmail@gmail.com")
                .orderBy("regTime",true)
//                .limit(2) //只查询两条记录
//                .offset(2) //偏移两个,从第三个记录开始返回,limit配合offset达到sqlite的limit m,n的查询
                .findAll();
        if(users == null || users.size() == 0) {
            return null;//请先调用dbAdd()方法
        }
        return users;
    }

}
