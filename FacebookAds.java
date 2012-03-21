import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
public class FacebookAds extends Composite {
  private Browser browser;
  public FacebookAds(Composite parent) {
    super(parent, SWT.NONE);
    GridLayout layout = new GridLayout(2, true);
    setLayout(layout);
    browser = new Browser(this, SWT.NONE);
    GridData layoutData = new GridData(GridData.FILL_BOTH);
    layoutData.horizontalSpan = 2;
    layoutData.verticalSpan = 2;
    browser.setLayoutData(layoutData);
    browser.setUrl("http://www.facebook.com");
  }
}