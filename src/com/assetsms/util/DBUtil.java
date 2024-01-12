package com.assetsms.util;


import java.sql.*;

import static com.assetsms.util.Config.getValue;


public class DBUtil {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    /**
     * 得到数据库连接
     */
    public Connection getConnection() throws ClassNotFoundException,
            SQLException, InstantiationException, IllegalAccessException {
        // 通过Config获取Oracle数据库配置信息
        String driver = getValue("driver");
        String url = getValue("url");
        String user = getValue("username");
        String pwd = getValue("password");
        try {
            // 指定驱动程序
            Class.forName(driver);
            // 建立数据库连结
            conn = DriverManager.getConnection(url, user, pwd);
            return conn;
        } catch (Exception e) {
            // 如果连接过程出现异常，抛出异常信息
            throw new SQLException("驱动错误或连接失败！");
        }
    }

    /**
     * 释放资源
     */
    public void closeAll() throws Exception{
        // 如果rs不空，关闭rs
        if (rs != null) {
            rs.close();
        }
        // 如果pstmt不空，关闭pstmt
        if (pstmt != null) {
            pstmt.close();
        }
        // 如果conn不空，关闭conn
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * 执行SQL语句，可以进行查询
     */
    public ResultSet executeQuery(String preparedSql, Object ...param) throws Exception {
        // 处理SQL,执行SQL
        // 得到PreparedStatement对象
        pstmt = conn.prepareStatement(preparedSql);
        if (param != null) {
            for (int i = 0; i < param.length; i++) {
                // 为预编译sql设置参数

                pstmt.setObject(i + 1, param[i]);
            }
        }
        // 执行SQL语句
        rs = pstmt.executeQuery();
        return rs;
    }

    /**
     * 执行SQL语句，可以进行增、删、改的操作，不能执行查询
     */
    public int executeUpdate(String preparedSql, Object ...param) throws Exception {
        int num = 0;
        // 处理SQL,执行SQL
        // 得到PreparedStatement对象
        pstmt = conn.prepareStatement(preparedSql);
        if (param != null) {
            for (int i = 0; i < param.length; i++) {
                // 为预编译sql设置参数
                pstmt.setObject(i + 1, param[i]);
            }
        }
        // 执行SQL语句
        num = pstmt.executeUpdate();

        return num;
    }
}
