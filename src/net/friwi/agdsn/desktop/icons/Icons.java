package net.friwi.agdsn.desktop.icons;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.friwi.agdsn.desktop.network.NetworkState.NetworkStateEnum;

public class Icons {
	static HashMap<String, BufferedImage> icons = new HashMap<String, BufferedImage>();
	public static void init(){
		for(int i = 0; i<=100; i+=5){
			load(i+"");
		}
		load("active");
		load("inactive");
		load("blocked");
		load("logo_16");
		load("logo_48");
		load("logo_64");
		load("logo_128");
	}
	public static void load(String path) {
		try{
			BufferedImage img = ImageIO.read(Icons.class.getResourceAsStream(path+".png"));
			icons.put(path, img);
		}catch(Exception e){
			System.err.println("Error loading icon "+path+".png:");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	public static BufferedImage getIcon(float percentage){
		for(float i = 0; i<=1F; i+=0.05F){
			if(percentage<=i+0.025F){
				return icons.get((int)(i*100)+"");
			}
		}
		return icons.get("100");
	}
	public static BufferedImage getStateIcon(NetworkStateEnum networkStateEnum){
		return icons.get(networkStateEnum==NetworkStateEnum.CONNECTED?"active":(networkStateEnum==NetworkStateEnum.BLOCKED?"blocked":"inactive"));
	}
	public static BufferedImage getLogo(int size){
		switch(size){
		case 0:  return icons.get("logo_16");
		case 1:  return icons.get("logo_48");
		case 2:  return icons.get("logo_64");
		case 3:  return icons.get("logo_128");
		default: return null;
		}
	}
}
