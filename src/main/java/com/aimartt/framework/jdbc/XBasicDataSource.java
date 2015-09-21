package com.aimartt.framework.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * 应用程序注册了JDBC驱动，但当程序停止时无法注销这个驱动，tomcat为了防止内存溢出，就给强制注销了
 * @author OZHIBIN
 */
public class XBasicDataSource extends BasicDataSource {
	
	@Override
	public synchronized void close() throws SQLException {
		// System.out.println("......输出数据源Driver的url："+DriverManager.getDriver(url));
		DriverManager.deregisterDriver(DriverManager.getDriver(url));
		super.close();
	}
	
}