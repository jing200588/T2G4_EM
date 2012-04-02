package emserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class EMSService {

	private Server server;
	
	public EMSService() {
		this(8080);
	}
	public EMSService(Integer aPort) {
		server = new Server(aPort);
	}
	
	public void setHandler(ContextHandlerCollection contexts) {
		server.setHandler(contexts);
	}
	
	public void start() throws Exception {
		server.start();
	}
	
	public void stop() throws Exception {
		server.stop();
		server.join();
	}
	
	public boolean isStarted() {
		return server.isStarted();
	}
	
	public boolean isStopped() {
		return server.isStopped();
	}
}