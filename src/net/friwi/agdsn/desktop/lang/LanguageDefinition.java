package net.friwi.agdsn.desktop.lang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class LanguageDefinition {
	private String code;
	private HashMap<String, String> translations;
	public static char[] replacements = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	public LanguageDefinition(String code){
		this.code = code;
		load();
	}
	private void load(){
		translations = new HashMap<String, String>();
		try{
			BufferedReader read = new BufferedReader(new InputStreamReader(LanguageDefinition.class.getResourceAsStream(code+".lang")));
			String str;
			while((str=read.readLine())!=null){
				if(!str.startsWith("#")&&str.length()>2){
					String[] s = str.split("=");
					translations.put(s[0], s[1]);
				}
			}
			read.close();
		}catch(Exception e){
			System.err.println("Error parsing language definition "+code+".lang");
			e.printStackTrace();
		}
	}
	public String getTranslation(String t, String... args){
		String ret = translations.get(t);
		if(ret==null)return "err.translation_not_defined:"+t;
		for(int i = 0; i<args.length; i++){
			ret = ret.replace("%"+replacements[i], args[i]);
		}
		return ret;
	}
	public String getLanguageCode() {
		return code;
	}
}
