package tenx.gwt.portfolio.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PortfolioMain implements EntryPoint {

	private static final Logger LOGGER = Logger.getLogger(PortfolioMain.class.getName());
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				LOGGER.log(Level.SEVERE,"uncaught exception",e);
			}
		});
		
		RootPanel.get("main")
			.add(new PortfolioDetailsWidget(1));
	}

}
