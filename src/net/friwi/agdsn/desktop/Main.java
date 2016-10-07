package net.friwi.agdsn.desktop;

import net.friwi.agdsn.desktop.config.ConfigManager;
import net.friwi.agdsn.desktop.icons.Icons;
import net.friwi.agdsn.desktop.lang.LanguageDefinition;
import net.friwi.agdsn.desktop.network.NetworkState;
import net.friwi.agdsn.desktop.network.NetworkSystem;
import net.friwi.agdsn.desktop.ui.Notificator;

public class Main {
	public static final String version = "1.0.0";
	public static LanguageDefinition langDef = null;
	public static void main(String args[]){
		System.out.println("###########################################");
		System.out.println("# AG DSN Desktop Client version "+version+"     #");
		System.out.println("# written by Fritz Windisch               #");
		System.out.println("# (c) 2016-2020 Fritz Windisch            #");
		System.out.println("###########################################");
		
		ConfigManager.init();
		langDef = new LanguageDefinition(ConfigManager.getLanguage());
		System.out.print("> Loading avaiable icons...");
		Icons.init();
		System.out.println(" Done!");
		System.out.print("> Registering tray icon...");
		NetworkSystem.setNetworkStateSilent(new NetworkState(0, 0, 0, 0));
		Notificator.update();
		System.out.println(" Done!");
		NetworkSystem.startNetworkPollThread();
	}
	public static void updateLanguage(String code) {
		ConfigManager.setLanguage(code);
		langDef = new LanguageDefinition(ConfigManager.getLanguage());
		Notificator.update();
	}
	public static void exit() {
		Notificator.destroy();
		System.exit(0);
	}
}
