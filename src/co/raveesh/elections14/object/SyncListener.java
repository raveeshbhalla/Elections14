package co.raveesh.elections14.object;

public interface SyncListener {
	public void onSyncStarted();
	public void onSyncComplete();
	public void onSyncFailed();
}
