package net.friwi.agdsn.desktop.util;

public class UnitUtil {
	public static String convertToByteUnit(double amount_kb){
		int unit = 0; //KB
		if(amount_kb>1024){
			unit = 1; //MB
			amount_kb /= 1024.;
			if(amount_kb>1024){
				unit = 2; //GB
				amount_kb /= 1024.;
			}
		}
		double round = (Math.round(amount_kb*100D)+0D)/100D;
		return round+" "+(unit==0?"KB":(unit==1?"MB":"GB"));
	}
}
