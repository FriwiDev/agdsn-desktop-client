package net.friwi.agdsn.desktop.ui;

import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import net.friwi.agdsn.desktop.network.NetworkSystem;
import net.friwi.agdsn.desktop.network.NetworkState.NetworkStateEnum;

public class WebTrafficMenuItem extends MenuItem{

	private static final long serialVersionUID = 1L;

	public WebTrafficMenuItem(String text){
		super(text);
		this.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URI("https://agdsn.de/sipa/usertraffic"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		if(NetworkSystem.getNetworkState().getState()!=NetworkStateEnum.CONNECTED)this.setEnabled(false);
	}
}
