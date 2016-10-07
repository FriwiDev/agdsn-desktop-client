package net.friwi.agdsn.desktop.ui;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import net.friwi.agdsn.desktop.Main;
import net.friwi.agdsn.desktop.icons.Icons;
import net.friwi.agdsn.desktop.network.NetworkState;
import net.friwi.agdsn.desktop.network.NetworkState.NetworkStateEnum;
import net.friwi.agdsn.desktop.network.NetworkSystem;
import net.friwi.agdsn.desktop.util.UnitUtil;

public class Notificator {
	private static TrayIcon mt = null;
	public static void setupTrayIcon(Image icon) {
		if(mt==null){
			SystemTray mainTray = SystemTray.getSystemTray();
			TrayIcon mainTrayIcon = new TrayIcon(icon);
			mainTrayIcon.setImageAutoSize(true);
			try {
			    mainTray.add(mainTrayIcon);
			    mainTrayIcon.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						try{
							Desktop.getDesktop().browse(NetworkSystem.getNetworkState().getState()==NetworkStateEnum.DISCONNECTED?new URI("https://agdsn.de/"):new URI("https://agdsn.de/sipa/usertraffic"));
						}catch(Exception e){
							e.printStackTrace();
						}
					}
			    	
			    });
			    mt = mainTrayIcon;
			}catch (Exception e) {
			    e.printStackTrace();
			}
		}else{
			mt.setImage(icon);
		}
	}
	public static void setTooltip(String tooltip){
		mt.setToolTip(tooltip);
	}
	public static void sendNotification(String title, String subtitle) {
	    try {
	    	mt.displayMessage(title,  subtitle, TrayIcon.MessageType.NONE);
	    }catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	private static void updateContextMenu(){
		PopupMenu m = new PopupMenu();
		if(NetworkSystem.getNetworkState().getState()==NetworkStateEnum.CONNECTED){
			m.add(new DisabledMenuItem(Main.langDef.getTranslation("agdsn.remaining", UnitUtil.convertToByteUnit(NetworkSystem.getNetworkState().quota), UnitUtil.convertToByteUnit(NetworkState.max_save))));
			m.add(new DisabledMenuItem(Main.langDef.getTranslation("agdsn.today", UnitUtil.convertToByteUnit(NetworkSystem.getNetworkState().in+NetworkSystem.getNetworkState().out), UnitUtil.convertToByteUnit(NetworkState.daily))));
		}else if(NetworkSystem.getNetworkState().getState()==NetworkStateEnum.DISCONNECTED){
			m.add(new DisabledMenuItem(Main.langDef.getTranslation("agdsn.offline1")));
			m.add(new DisabledMenuItem(Main.langDef.getTranslation("agdsn.offline2")));
		}else{
			m.add(new DisabledMenuItem(Main.langDef.getTranslation("agdsn.blocked1")));
			m.add(new DisabledMenuItem(Main.langDef.getTranslation("agdsn.blocked2")));
		}
		m.addSeparator();
		m.add(new LanguageMenuItem("de_DE_DU", "Deutsch (Du)"));
		m.add(new LanguageMenuItem("de_DE_SIE", "Deutsch (Sie)"));
		m.add(new LanguageMenuItem("en_UK", "English (UK)"));
		m.addSeparator();
		m.add(new WebTrafficMenuItem(Main.langDef.getTranslation("agdsn.menu.web_traffic")));
		m.add(new PopupTrafficMenuItem(Main.langDef.getTranslation("agdsn.menu.popup_traffic")));
		m.addSeparator();
		m.add(new AboutMenuItem(Main.langDef.getTranslation("agdsn.menu.about")));
		m.add(new ExitMenuItem(Main.langDef.getTranslation("agdsn.menu.exit")));
		mt.setPopupMenu(m);
	}
	public static void destroy() {
		if(mt==null)return;
		SystemTray mainTray = SystemTray.getSystemTray();
		mainTray.remove(mt);
	}
	public static void update() {
		if(NetworkSystem.getNetworkState().getState()==NetworkStateEnum.CONNECTED){
			float proc = (NetworkSystem.getNetworkState().quota+0F)/(NetworkState.max_save+0F);
			setupTrayIcon(Icons.getIcon(proc));
			int proci = (int)(proc*100);
			setTooltip(Main.langDef.getTranslation("agdsn.tt_connected", proci+"%", UnitUtil.convertToByteUnit(NetworkSystem.getNetworkState().quota), UnitUtil.convertToByteUnit(NetworkState.max_save)));
		}else{
			setupTrayIcon(Icons.getStateIcon(NetworkSystem.getNetworkState().getState()));
			if(NetworkSystem.getNetworkState().getState()==NetworkStateEnum.BLOCKED){
				setTooltip(Main.langDef.getTranslation("agdsn.tt_blocked"));
			}else{
				setTooltip(Main.langDef.getTranslation("agdsn.tt_disconnected"));
			}
		}
		updateContextMenu();
	}
}
