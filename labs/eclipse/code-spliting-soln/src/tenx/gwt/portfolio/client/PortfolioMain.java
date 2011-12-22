package tenx.gwt.portfolio.client;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import tenx.gwt.portfolio.client.common.Presenter;
import tenx.gwt.portfolio.client.detail.PortfolioDetailsPresenter;
import tenx.gwt.portfolio.client.detail.PortfolioDetailsPresenterImpl;
import tenx.gwt.portfolio.client.detail.PortfolioDetailsViewImpl;
import tenx.gwt.portfolio.client.list.PortfolioListPresenterImpl;
import tenx.gwt.portfolio.client.list.PortfolioListViewImpl;
import tenx.gwt.portfolio.client.rpc.PortfolioService;
import tenx.gwt.portfolio.client.rpc.PortfolioServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
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

	PortfolioServiceAsync portfolioServiceAsync  = GWT.create(PortfolioService.class);
	
	private static final Logger LOGGER = Logger.getLogger(PortfolioMain.class.getName());
	
	private SimplePanel mainWidget;

	private Presenter currentPresenter;

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
		
		GWT.runAsync(new RunAsyncCallback() {
			@Override
			public void onSuccess() {
				if (!isCurrentPresenterOfType(PortfolioListPresenterImpl.class)) {
					currentPresenter = new PortfolioListPresenterImpl(new PortfolioListViewImpl());
					mainWidget.clear();
					currentPresenter.bind();
					currentPresenter.go(mainWidget);
				}
			}
			
			@Override
			public void onFailure(Throwable reason) {
			}
		});
		
	}

	protected void doShowPortfolio(final int portfolioId) {
		
		GWT.runAsync(new RunAsyncCallback() {
			@Override
			public void onSuccess() {
				if (!isCurrentPresenterOfType(PortfolioDetailsPresenterImpl.class)) {
					LOGGER.info("view not portfolio details - creating new default portoflio details view");
					currentPresenter = new PortfolioDetailsPresenterImpl(portfolioId, new PortfolioDetailsViewImpl(),portfolioServiceAsync);
					mainWidget.clear();
					currentPresenter.bind();
					currentPresenter.go(mainWidget);
				} else {
					LOGGER.info("changing the portfoio id to " + portfolioId);
					((PortfolioDetailsPresenter)currentPresenter).setPortfolioId(portfolioId);
				}
			}
			
			@Override
			public void onFailure(Throwable reason) {
			}
		});
		
		
		
	}

	private <T> boolean isCurrentPresenterOfType(Class<T> type) {
		return currentPresenter != null 
				&& currentPresenter.getClass().getName().equals(type.getName());
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
