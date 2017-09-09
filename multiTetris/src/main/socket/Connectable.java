package main.socket;

import java.io.OutputStream;

public interface Connectable {

	public void connect();
	
	public void disConnect();
	
	public void send();
	
	public void receive();
	
	public void setConnectListener(ConnectListener listener);
	
	public OutputStream getOutputStream();
	
	public void setPlayGameListener(PlayGameListener playGameListener);
}
