package com.heiji;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * nginx的日志格式配置代码 http {  userid on; log_format main
 * '$remote_addr^$time_local^$request^$status^$body_bytes_sent^$http_referer^$http_user_agent^$uid_got
 * $http_cookie'; access_log logs/access.log main;  }
 * 
 * @author zu
 * 
 */
public class Nginx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public static DBObject getJson(String log) {
		String tempa = log;
		String[] aa = tempa.split("\\^");
		DBObject _b = new BasicDBObject();
		if (aa.length == 9) {
			_b.put("ip", aa[0]);
			_b.put("time", aa[1]);
			// method 内容包含url GET /favicon.ico HTTP/1.1
			String m = aa[2];
			String[] mm = m.split(" ");
			if (mm.length == 3) {
				_b.put("method", mm[0]);
				_b.put("url", mm[1]);
				_b.put("http", mm[2]);
			}
			_b.put("status", aa[3]);
			_b.put("浏览器", aa[6]);
			_b.put("cookie", aa[7]);
			_b.put("postData", aa[8]);
			_b.put("log", log);
		}
		return _b;
	}

}
