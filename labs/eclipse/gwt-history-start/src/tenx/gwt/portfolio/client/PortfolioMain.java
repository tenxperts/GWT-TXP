package tenx.gwt.portfolio.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PortfolioMain implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		SimplePanel mainWidget = new SimplePanel();
		mainWidget.setWidget(new PortfolioListWidget());
		RootPanel.get("main")
			.add(mainWidget);
	}
}
