package net.friwi.agdsn.desktop.ui;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.friwi.agdsn.desktop.Main;
import net.friwi.agdsn.desktop.network.NetworkState;
import net.friwi.agdsn.desktop.network.NetworkState.NetworkStateEnum;
import net.friwi.agdsn.desktop.network.NetworkSystem;
import net.friwi.agdsn.desktop.util.UnitUtil;

public class PopupTrafficMenuItem extends MenuItem{

	private static final long serialVersionUID = 1L;

	public PopupTrafficMenuItem(String text){
		super(text);
		this.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Notificator.sendNotification("AG DSN - "+Main.langDef.getTranslation("agdsn.online"), Main.langDef.getTranslation("agdsn.traffic_notification", UnitUtil.convertToByteUnit(NetworkSystem.getNetworkState().quota), UnitUtil.convertToByteUnit(NetworkState.max_save)));
			}
			
		});
		if(NetworkSystem.getNetworkState().getState()!=NetworkStateEnum.CONNECTED)this.setEnabled(false);
	}
}
