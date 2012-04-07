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
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;



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
		formViewServer.getHead().setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		formViewServer.getHead().setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		formViewServer.getBody().setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		formViewServer.setBounds(0, 0, 700, 400);
		formViewServer.getHead().setFont(SWTResourceManager.getFont("Showcard Gothic", 20, SWT.NORMAL));
		formToolkit.paintBordersFor(formViewServer);
		formViewServer.setText("Server Controls");
		formViewServer.getBody().setLayout(new FormLayout());
		compMain = new Composite(formViewServer.getBody(), SWT.NONE);
		FormData fd_compMain = new FormData();
		fd_compMain.bottom = new FormAttachment(0);
		fd_compMain.right = new FormAttachment(0);
		fd_compMain.top = new FormAttachment(0);
		fd_compMain.left = new FormAttachment(0);
		compMain.setLayoutData(fd_compMain);
		
		
		final Button btnStart = new Button(formViewServer.getBody(), SWT.NONE);
		btnStart.setFont(SWTResourceManager.getFont("Maiandra GD", 12, SWT.NORMAL));
		FormData fd_btnStart = new FormData();
		fd_btnStart.height = 50;
		fd_btnStart.right = new FormAttachment(45);
		fd_btnStart.top = new FormAttachment(20);
		btnStart.setLayoutData(fd_btnStart);
		final Button btnStop = new Button(formViewServer.getBody(), SWT.NONE);
		btnStop.setFont(SWTResourceManager.getFont("Maiandra GD", 12, SWT.NORMAL));
		FormData fd_btnStop = new FormData();
		fd_btnStop.height = 50;
		fd_btnStop.top = new FormAttachment(20);
		fd_btnStop.left = new FormAttachment(55);
		btnStop.setLayoutData(fd_btnStop);
		formToolkit.adapt(btnStart, true, true);
		btnStart.setText("Start Server");
		formToolkit.adapt(btnStop, true, true);
		btnStop.setText("Stop Server");
		
		txtServerPort = new Text(formViewServer.getBody(), SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		txtServerPort.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		fd_btnStop.right = new FormAttachment(txtServerPort, 0, SWT.RIGHT);
		fd_btnStart.left = new FormAttachment(txtServerPort, 0, SWT.LEFT);
		FormData fd_txtServerPort = new FormData();
		fd_txtServerPort.top = new FormAttachment(btnStart, 20);
		fd_txtServerPort.bottom = new FormAttachment(80);
		fd_txtServerPort.right = new FormAttachment(80);
		fd_txtServerPort.left = new FormAttachment(20);
		txtServerPort.setLayoutData(fd_txtServerPort);
		txtServerPort.setText("Default Server port is 3662. \n\nNavigate to http://localhost:3662 in your preferred browser");
		txtServerPort.setEditable(false);
		formToolkit.adapt(txtServerPort, true, true);

		btnStart.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnStop.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
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
