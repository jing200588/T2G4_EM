package emserver;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

public class EMSMain {
	
	public static void main(String[] args) throws Exception
	{
	    
		
			
		
		WebAppContext context = new WebAppContext();
		context.setDescriptor(context + "/WEB-INF/web.xml");
		context.setResourceBase(".");
		context.setContextPath("/");

		
	    
	    ContextHandlerCollection contexts = new ContextHandlerCollection();
	    Handler[] handleSet = new Handler[1];
	    handleSet[0] = new AppContextBuilder().build();
		contexts.setHandlers(handleSet);

		EMSService server = new EMSService();
	    server.setHandler(contexts);
	    
	    server.start();
		
	}
}
