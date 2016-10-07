package net.friwi.agdsn.desktop.network;

import org.json.JSONObject;

public class PollingUtil {
	private static JSONQuery query = null;
	static{
		query = new JSONQuery("agdsn.de", "/sipa/usertraffic/json");
	}
	public static JSONObject getState() throws Exception{
		if(query!=null)return query.execute();
		return null;
	}
	
	public static NetworkState pollNetwork(){
		try{
			System.out.println("Polling network...");
			JSONObject obj = getState();
			int version = obj.has("version")?obj.getInt("version"):0;
			long quota = obj.has("quota")?obj.getLong("quota"):0;
			JSONObject traffic = obj.has("traffic")?obj.getJSONObject("traffic"):null;
			long in = 0;
			long out = 0;
			if(traffic!=null){
				in = traffic.has("in")?traffic.getLong("in"):0;
				out = traffic.has("out")?traffic.getLong("out"):0;
			}
			System.out.println("Network polling succeded");
			return new NetworkState(version, quota, in, out);
		}catch(Exception e){
			System.out.println("Network polling failed");
			e.printStackTrace();
			return new NetworkState(0, 0, 0, 0);
		}
	}
}
