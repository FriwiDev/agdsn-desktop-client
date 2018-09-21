package net.friwi.agdsn.desktop.network;

import net.friwi.agdsn.desktop.Main;
import net.friwi.agdsn.desktop.network.NetworkState.NetworkStateEnum;
import net.friwi.agdsn.desktop.ui.Notificator;
import net.friwi.agdsn.desktop.util.UnitUtil;

public class NetworkSystem {
	private static NetworkState state = null;
	public static final long GB = 1024 * 1024; //All values delivered by SIPA are in KB format
	public static final long MB = 1024; //All values delivered by SIPA are in KB format
	//Values triggering a new message when reached by quota
	private static long[] significant_traffic_values = new long[]{
		100 * MB,
		200 * MB,
		500 * MB,
		1 * GB,
		2 * GB,
		3 * GB,
		5 * GB,
		7 * GB,
		10 * GB,
		15 * GB,
		25 * GB,
		40 * GB,
		60 * GB,
		80 * GB,
		100 * GB,
		150 * GB,
		200 * GB,
	};
	public static void setNetworkState(NetworkState state){
		setNetworkState(false, state);
	}
	public static void setNetworkState(boolean force_message, NetworkState state){
		NetworkState old_state = NetworkSystem.state;
		NetworkSystem.state = state;
		//Update data
		Notificator.update();
		//Push notifications
		if(state.getState()==NetworkStateEnum.CONNECTED){
			if(old_state.getState()==NetworkStateEnum.CONNECTED){
				if(old_state.quota<state.quota){
					//New traffic :)
					Notificator.sendNotification("AG DSN - "+Main.langDef.getTranslation("agdsn.online"), Main.langDef.getTranslation("agdsn.new_traffic", UnitUtil.convertToByteUnit(getNetworkState().quota), UnitUtil.convertToByteUnit(NetworkState.max_save)));
				}else{
					//Triggering a message when a significant traffic value is reached
					for(long sv : significant_traffic_values){
						if(sv<old_state.quota&&sv>=state.quota){
							Notificator.sendNotification("AG DSN - "+Main.langDef.getTranslation("agdsn.online"), Main.langDef.getTranslation("agdsn.traffic_notification", UnitUtil.convertToByteUnit(getNetworkState().quota), UnitUtil.convertToByteUnit(NetworkState.max_save)));
							break;
						}
					}
				}
			}else if(old_state.getState()==NetworkStateEnum.DISCONNECTED){
				//Offline->Online
				Notificator.sendNotification("AG DSN - "+Main.langDef.getTranslation("agdsn.online"), Main.langDef.getTranslation("agdsn.online_now", UnitUtil.convertToByteUnit(getNetworkState().quota), UnitUtil.convertToByteUnit(NetworkState.max_save)));
			}else{
				//Blocked->Online
				Notificator.sendNotification("AG DSN - "+Main.langDef.getTranslation("agdsn.online"), Main.langDef.getTranslation("agdsn.unblocked", UnitUtil.convertToByteUnit(getNetworkState().quota)));
			}
		}else if(state.getState()==NetworkStateEnum.DISCONNECTED){
			if(old_state.getState()==NetworkStateEnum.CONNECTED){
				//Online->Offline
				Notificator.sendNotification("AG DSN - "+Main.langDef.getTranslation("agdsn.offline"), Main.langDef.getTranslation("agdsn.offline_now"));
			}else if(old_state.getState()==NetworkStateEnum.DISCONNECTED){
				//Offline->Offline
			}else{
				//Blocked->Offline
				Notificator.sendNotification("AG DSN - "+Main.langDef.getTranslation("agdsn.offline"), Main.langDef.getTranslation("agdsn.offline_now"));
			}
		}else{
			if(old_state.getState()==NetworkStateEnum.CONNECTED){
				//Online->Blocked
				Notificator.sendNotification("AG DSN - "+Main.langDef.getTranslation("agdsn.blocked"), Main.langDef.getTranslation("agdsn.blocked_now"));
			}else if(old_state.getState()==NetworkStateEnum.DISCONNECTED){
				//Offline->Blocked
				Notificator.sendNotification("AG DSN - "+Main.langDef.getTranslation("agdsn.blocked"), Main.langDef.getTranslation("agdsn.blocked_still"));
			}else{
				//Blocked->Blocked
			}
		}
	}
	public static NetworkState getNetworkState(){
		return state;
	}
	public static void updateNetwork(){
		setNetworkState(PollingUtil.pollNetwork());
	}
	public static void updateNetwork(boolean force_message){
		setNetworkState(force_message, PollingUtil.pollNetwork());
	}
	public static void startNetworkPollThread(){
		new Thread(){
			public void run(){
				setName("Network-Poller");
				while(true){
					updateNetwork();
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	public static void setNetworkStateSilent(NetworkState poll) {
		state = poll;
	}
}
