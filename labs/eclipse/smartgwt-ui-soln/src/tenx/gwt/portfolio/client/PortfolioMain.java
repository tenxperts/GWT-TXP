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
		
		TabSet tabContainer = new TabSet();
		tabContainer.setWidth100();
		tabContainer.setHeight100();
		createPortfolioTab(tabContainer, 1);
		
		Tab instructions = new Tab("Instructions");
		HTMLPane pane = new HTMLPane();
		pane.setContentsURL("instructions.html");
		instructions.setPane(pane);
		
		tabContainer.addTab(instructions);
		
//		createPortfolioTab(tabContainer, 2);
		
		RootPanel.get("main")
			.add(tabContainer);
	}

	private void createPortfolioTab(TabSet tabContainer, int portfolioId) {
		Tab portfolioDetailsTab = new Tab("Portfolio Details - " + portfolioId);
		portfolioDetailsTab.setPane(new WidgetCanvas(new PortfolioDetailsWidget(1)));
		tabContainer.addTab(portfolioDetailsTab);
	}
}
