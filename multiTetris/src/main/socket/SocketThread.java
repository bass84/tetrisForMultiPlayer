package main.socket;


public class SocketThread implements Runnable{
	
	Connectable socket;
	
	public SocketThread(Connectable socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		socket.connect();
	}

}
