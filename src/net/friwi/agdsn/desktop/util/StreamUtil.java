package net.friwi.agdsn.desktop.util;

import java.io.IOException;
import java.io.Reader;

public class StreamUtil {
	/**
	 * Copy-paste from: http://stackoverflow.com/questions/4308554
	 */
	public static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    try{
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
	    }catch(Exception e){
	    	
	    }
	    return sb.toString();
	}
}
