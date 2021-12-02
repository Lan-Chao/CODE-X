package com.silent.base.application;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * @author zhao
 * @date 2021/12/2 11:05
 */
public class DataSourceDemo {


    // https://blog.csdn.net/lovebosom/article/details/54693362



    public DataSource createDataSource() {
        PoolProperties pp = new PoolProperties();
        pp.setDriverClassName("com.mysql.jdbc.Driver");
        pp.setUrl("jdbc:mysql://localhost:3306/mysql");
        pp.setUsername("root");
        pp.setPassword("password");
        DataSource dataSource = new DataSource(pp);

        // 设置连接池建立时连接的数目
        dataSource.setInitialSize(10);
        // 连接数据库的最大连接数。这个属性用来限制连接池中能够打开连接的数量，可以方便数据库做连接容量规划。
        dataSource.setMaxActive(10);
        // 连接池中存在的最小连接数目。
        // 连接池中连接数目可以变很少，如果使用了maxAge属性，有些空闲的连接会被关闭因为离它最近一次连接的时间过去太久了。
        // 但是，我们看到的打开的连接不会少于minIdle。
        dataSource.setMinIdle(10);
        // 它的不同的行为取决于是否使用了pool sweeper。
        // pool sweeper是一个可以在连接池正在使用的时候测试空闲连接和重置连接池大小的后台线程。还负责检测连接泄露。
        dataSource.setMaxIdle(10);

        dataSource.isPoolSweeperEnabled();




        return dataSource;
    }




}
