package tenx.gwt.portfolio.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PortfolioMain implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		JavaScriptHelper.publish();
		JavaScriptHelper.sayHello("Kamal");
		RootPanel.get("main")
			.add(new PortfolioDetailsWidget());
	}
	
}
