package cn.yuol.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.yuol.exception.MyException;

public class DBUtils {
	private static final String DB = "db.properties";
	private static final String DRIVER = "driver";
	private static final String URL = "url";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static final String PREFIX = "prefix";
	private static InputStream in;
	private static Properties pro=new Properties();
	static {
		in = DBUtils.class.getClassLoader().getResourceAsStream(DB);
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnect() throws Exception{
			    String driver=pro.getProperty(DRIVER);
			    String url=pro.getProperty(URL);
			    String user=pro.getProperty(USER);
			    String password=pro.getProperty(PASSWORD);
				Class.forName(driver);
				return DriverManager.getConnection(url,user,password);
	}

	public static Map<String, String> getConfig() throws MyException {
		Map<String, String> map = new HashMap<String, String>();
			map.put(PREFIX, pro.getProperty("prefix"));
			return map;
	}
	/*public static void Close() throws MyException{
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					throw new MyException(e);
				}
	}*/
}
