package cn.ictt.zhanghui.eureka_provider.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

@Slf4j
public class PropertiesUtil {
	private static Properties props;
	static {
		loadProps();
	}
	/**
	 * 加载属性文件
	 * @author ZhangHui
	 * @date 2019/6/20
	 * @param
	 * @return void
	 */
	synchronized static  void loadProps() {
		props = new Properties();
		InputStream in = null;
		try {
			// 第一种，通过类加载器进行获取properties文件流
			// in = PropertyUtil.class.getClassLoader().getResourceAsStream("sysConfig.properties");
			// 第二种，通过类进行获取properties文件流
			in = PropertiesUtil.class.getResourceAsStream("/my_config.properties");
			props.load(new InputStreamReader(in, "utf-8"));
		} catch (FileNotFoundException e) {
			 log.error("sysConfig.properties文件未找到");
		} catch (IOException e) {
			 log.error("出现IOException");
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				 log.error("sysConfig.properties文件流关闭出现异常");
			}
		}
	}
	/**
	 * 根据key取属性文件的value
	 * @author ZhangHui
	 * @date 2019/6/20
	 * @param key
	 * @return java.lang.String
	 */
	public static String getProperty(String key) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key);
	}
	/**
	 * 根据key取属性文件的value，没有value返回defaultValue
	 * @author ZhangHui
	 * @date 2019/6/20
	 * @param key
	 * @param defaultValue
	 * @return java.lang.String
	 */
	public static String getProperty(String key, String defaultValue) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key, defaultValue);
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getProperty("aaa"));
	}
}
