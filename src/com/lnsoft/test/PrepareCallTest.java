package com.lnsoft.test;

import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created By Chr on 2019/1/5/0005.
 *
 * 该方法调用oracle的存储过程的Demo
 */
public class PrepareCallTest {
    public static void main(String args[]) throws Exception {
        //1.加载驱动，反射
        /**
         * 如何找包，在obdbc6.jar中，oracle的jdbc的driver的OracleDriver中有OracleDriver.java文件
         */
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //2.获取连接对象
        String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
        String user = "scott";
        String pwd = "etoak";
        Connection connection = DriverManager.getConnection(url, user, pwd);
        //3.获得语句对象,调用存储过程
        String sql = "{call p_querysal_out(?,?)}";
        CallableStatement callableStatement = connection.prepareCall(sql);
        //4.设置输入参数,下标从1开始,第一个参数是下标，第二个是入参
        callableStatement.setInt(1, 7788);
        //5.注册输出参数，必须先注册输出参数，第一个参数是下标，第二个参数是OracleType，是一个常量
        callableStatement.registerOutParameter(2, OracleTypes.DOUBLE);
        //6.执行存储过程
        callableStatement.execute();
        //7.获取输出参数
        double sal = callableStatement.getDouble(2);
        System.out.println(sal);
        //8.释放资源
        callableStatement.close();
        connection.close();
    }
}
