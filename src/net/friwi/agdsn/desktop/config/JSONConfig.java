package net.friwi.agdsn.desktop.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.json.JSONObject;

import net.friwi.agdsn.desktop.util.StreamUtil;

public class JSONConfig {
	private File file;
	private JSONObject obj = null;
	public JSONConfig(File file){
		this.file = file;
	}
	public void load() throws Exception{
		BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String all = StreamUtil.readAll(read);
		obj = new JSONObject(all);
	}
	public JSONObject getObject(){
		return obj;
	}
	public void setObject(JSONObject obj){
		this.obj = obj;
	}
	public void save(){
		String to_save = obj.toString();
		try{
			file.getParentFile().mkdirs();
			file.createNewFile();
			PrintWriter write = new PrintWriter(new FileOutputStream(file));
			write.println(to_save);
			write.flush();
			write.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
