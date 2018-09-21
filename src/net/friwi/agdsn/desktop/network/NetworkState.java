package net.friwi.agdsn.desktop.network;

public class NetworkState {
	public static final long GB = 1024 * 1024; //All values delivered by SIPA are in KB format
	public static final long daily = 10 * GB;
	public static final long max_save = 210 * GB;
	public enum NetworkStateEnum{
		CONNECTED,
		DISCONNECTED,
		BLOCKED
	}
	private long version;
	public long quota;
	public long in;
	public long out;
	public NetworkState(long version, long quota, long in, long out){
		this.version = version;
		this.quota = quota;
		this.in = in;
		this.out = out;
	}
	public NetworkStateEnum getState(){
		if(version<=0)return NetworkStateEnum.DISCONNECTED;
		if(quota<=0)return NetworkStateEnum.BLOCKED;
		return NetworkStateEnum.CONNECTED;
	}
}
