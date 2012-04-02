package emserver;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;



public class ViewServer extends Composite {
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getCurrent());
	private Composite compMain;
	private Text txtServerPort;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewServer(Composite parent, int style, final EMSService server) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				formToolkit.dispose();
			}
		});


		formToolkit.adapt(this);
		formToolkit.paintBordersFor(this);

		
		
		setLayout(new FillLayout(SWT.HORIZONTAL));
		Form formViewServer = formToolkit.createForm(this);
		formViewServer.setBounds(0, 0, 700, 400);
		formViewServer.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(formViewServer);
		formViewServer.setText("Server Controls");
		compMain = new Composite(formViewServer.getBody(), SWT.NONE);
		
		
		final Button btnStart = new Button(formViewServer.getBody(), SWT.NONE);
		final Button btnStop = new Button(formViewServer.getBody(), SWT.NONE);

		btnStart.setBounds(10, 10, 154, 48);
		formToolkit.adapt(btnStart, true, true);
		btnStart.setText("Start Server");
		
		btnStop.setBounds(188, 10, 154, 48);
		formToolkit.adapt(btnStop, true, true);
		btnStop.setText("Stop Server");
		
		txtServerPort = new Text(formViewServer.getBody(), SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		txtServerPort.setText("Default Server port is 8080. \n\nNavigate to http://localhost:8080 in your preferred browser");
		txtServerPort.setBounds(10, 74, 332, 82);
		txtServerPort.setEditable(false);
		formToolkit.adapt(txtServerPort, true, true);
		
		
		if(server.isStarted()){
			btnStart.setEnabled(false);
			btnStop.setEnabled(true);
		}else{
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
		}
		
		
		btnStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(server.isStopped()){
					 try {
						server.start();
						
						btnStart.setEnabled(false);
						btnStop.setEnabled(true);
						
					} catch (Exception exception) {
						
					}
				 }
			}
		});
		btnStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(server.isStarted()){
					 try {
						server.stop();
						btnStart.setEnabled(true);
						btnStop.setEnabled(false);
						
					} catch (Exception exception) {
						
					}
				 }
			}
		});

	}
}
