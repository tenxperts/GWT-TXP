package tenx.gwt.portfolio.client;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PortfolioMain implements EntryPoint {

	private static final Logger LOGGER = Logger.getLogger(PortfolioMain.class.getName());
	
	private SimplePanel mainWidget;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		mainWidget = new SimplePanel();
		RootPanel.get("main")
			.add(mainWidget);
		
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				LOGGER.info("history changed " + event.getValue());
				String token = event.getValue();
				if (token.equals("") || token.startsWith("list") || token.startsWith("home")) {
					doShowPortfolioList();
				} else if (token.startsWith("portfolio")) {
					Map<String,String> paramMap = constructParamMap(token.split(":")[1]);
					String id = paramMap.get("id");
					doShowPortfolio(id != null ? Integer.parseInt(id) : 1);
				} else {
					doShowPortfolio(1);
				}
			}
		});
		
		Window.addWindowClosingHandler(new ClosingHandler() {
			@Override
			public void onWindowClosing(ClosingEvent event) {
				event.setMessage("Are you sure you want to exit");
			}
		});
		
		History.fireCurrentHistoryState();

	}

	protected void doShowPortfolioList() {
		if (!isWidgetOfType(PortfolioListWidget.class)) {
			mainWidget.setWidget(new PortfolioListWidget());
		}
	}

	protected void doShowPortfolio(int portfolioId) {
		if (!isWidgetOfType(PortfolioDetailsWidget.class)) {
			LOGGER.info("view not portfolio details - creating new default portoflio details view");
			mainWidget.setWidget(new PortfolioDetailsWidget(portfolioId));
		} else {
			LOGGER.info("changing the portfoio id to " + portfolioId);
			((PortfolioDetailsWidget)mainWidget.getWidget()).show(portfolioId);
		}
		
		
	}

	private <T> boolean isWidgetOfType(Class<T> type) {
		return mainWidget.getWidget() != null 
				&& mainWidget.getWidget().getClass().getName().equals(type.getName());
	}

	protected Map<String, String> constructParamMap(String input) {
		String[] params = input.split(";");
		Map<String,String> result = new HashMap<String, String>();
		for(String param:params) {
			String[] parts = param.split("=");
			result.put(parts[0],parts[1]);
		}
		return result;
	}
}
