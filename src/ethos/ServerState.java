package ethos;

public enum ServerState {

	PUBLIC_PRIMARY(43569), PUBLIC_SECONDARY(43569), PRIVATE(43569);

	private int port;

	ServerState(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

}
