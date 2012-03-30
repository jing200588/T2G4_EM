package event;

import org.eclipse.swt.widgets.Display;


public class checkthread extends Thread {
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex) {
				this.yield();
			}
			 Display.getDefault().asyncExec(new Runnable() {
	               public void run() {
	                  ViewMain.CheckExpiry();
	                  
	          }
			 });
		}
	}
}
