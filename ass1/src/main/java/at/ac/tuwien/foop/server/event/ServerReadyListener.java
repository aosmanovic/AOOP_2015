package at.ac.tuwien.foop.server.event;

public interface ServerReadyListener {
	public void onReady();
	public void onFailure();
}
