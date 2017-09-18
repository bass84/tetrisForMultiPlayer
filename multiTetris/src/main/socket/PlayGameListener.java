package main.socket;

import main.keyEvent.GameKeyType.Information;

public interface PlayGameListener {
	
	public void sendTetrisInfo();
	
	public void sendTetrisInfo(Information information);
	
	public void resolveNetworkUserValue(String response);
	
}
