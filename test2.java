/*
 * ScrolledComposite example snippet: scroll a control in a scrolled composite
 *
 * For a list of all SWT example snippets see
 * http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/platform-swt-home/dev.html#snippets
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class test2 extends Composite{

  public test2(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
		
	    ScrolledComposite c1 = new ScrolledComposite(this, SWT.BORDER
	            | SWT.H_SCROLL | SWT.V_SCROLL);
	        Button b1 = new Button(c1, SWT.PUSH);
	        b1.setText("fixed size button");
	        b1.setSize(400, 400);
	        c1.setContent(b1);
	        c1.setBounds(0, 0, 300, 300);

	        // this button has a minimum size of 400 x 400. If the window is resized
	        // to be big
	        // enough to show more than 400 x 400, the button will grow in size. If
	        // the window
	        // is made too small to show 400 x 400, scrollbars will appear.
	        ScrolledComposite c2 = new ScrolledComposite(this, SWT.BORDER
	            | SWT.H_SCROLL | SWT.V_SCROLL);
	        Button b2 = new Button(c2, SWT.PUSH);
	        b2.setText("expanding button");
	        c2.setContent(b2);
	        c2.setExpandHorizontal(true);
	        c2.setExpandVertical(true);
	        c2.setMinWidth(400);
	        c2.setMinHeight(400);
	        c2.setBounds(350, 0, 300, 300);
	        
	}

public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setLayout(new FillLayout());

    // this button is always 400 x 400. Scrollbars appear if the window is
    // resized to be
    // too small to show part of the button

    test2 obj = new test2(shell, SWT.NONE);
    
    obj.pack();
    shell.setSize(600, 300);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }

}
