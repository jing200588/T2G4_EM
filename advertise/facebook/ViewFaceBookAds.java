package advertise.facebook;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Chua Hong Jing
 *
 */

public class ViewFaceBookAds extends Composite {

	public ViewFaceBookAds(Composite parent) {
		super(parent, SWT.NONE);
		
		Browser browser;
		GridLayout layout = new GridLayout(1, true);
		setLayout(layout);
		
		browser = new Browser(this, SWT.NONE);
		
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		
		layoutData.verticalSpan = 1;
		layoutData.horizontalSpan = 1;
		
		browser.setLayoutData(layoutData);
		browser.setUrl("https://www.facebook.com/dialog/feed?app_id=387929524559765&link=http://www.facebook.com/&picture=http://fbrell.com/f8.jpg&name=My%20Facebook%20Homepage&caption=%20&description=Upcoming%20Event%20Notice&redirect_uri=http://www.facebook.com/");
	}
}