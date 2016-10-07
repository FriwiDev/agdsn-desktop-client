package net.friwi.agdsn.desktop.config;

import java.io.File;

import org.json.JSONObject;

public class ConfigManager {
	private static JSONConfig conf = null;
	public static void init(){
		conf = new JSONConfig(new File(System.getProperty("user.home"), "agdsn-desktop.conf"));
		try{
			conf.load();
		}catch(Exception e){
			System.err.println("Failed to load config, using default values");
			conf.setObject(new JSONObject("{\"lang\":\"en_UK\"}"));
		}
	}
	public static String getLanguage(){
		return conf.getObject().getString("lang");
	}
	public static void setLanguage(String lang){
		conf.getObject().put("lang", lang);
		conf.save();
	}
}
