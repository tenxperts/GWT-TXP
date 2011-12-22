package tenx.gwt.portfolio.client;

import tenx.gwt.portfolio.client.overlay.PortfolioLite;
import tenx.gwt.portfolio.client.rpc.PortfolioService;
import tenx.gwt.portfolio.client.rpc.PortfolioServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class PortfolioListWidget extends Composite {

	private PortfolioServiceAsync portfolioServiceAsync = GWT.create(PortfolioService.class);
	private FlexTable portfolioList;
	
	public PortfolioListWidget() {
		portfolioList = new FlexTable();
		portfolioList.setBorderWidth(1);
		portfolioList.setCellSpacing(0);
		
		portfolioList.setWidget(0, 0, new Label("Name"));
		portfolioList.setWidget(0, 1, new Label("Description"));
		initWidget(portfolioList);
	}
	


	@Override
	protected void onLoad() {
		super.onLoad();
		loadUsingJson();
		loadUsingXml();
	}

	private void loadUsingXml() {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET,
				GWT.getHostPageBaseURL() + "portfolios_list.xml");
		
		rb.setCallback(new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() != 200 ) {
					Window.alert("unknown status code " + response.getStatusCode());
					return;
				}

				Document document =  XMLParser.parse(response.getText());
				NodeList portfolioNodes = document.getElementsByTagName("portfolio");
				
				int row = 1;
				for(int i=0;i<portfolioNodes.getLength();i++) {
					final Node portfolio = portfolioNodes.item(i);
					Anchor portfolioLink = new Anchor(portfolio.getAttributes().getNamedItem("name").getNodeValue());
					portfolioLink.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							History.newItem("portfolio:id=" + portfolio.getAttributes().getNamedItem("id").getNodeValue());
						}
					});
					portfolioList.setWidget(row, 0, portfolioLink);
					portfolioList.setWidget(row, 1, new Label(portfolio.getAttributes().getNamedItem("name").getNodeValue()));
					row++;
				}				
			}
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("request failed " + exception.getMessage());				
			}
		});
		
		rb.setHeader("Accept", "application/json");
		
		try {
			rb.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}



	public native JsArray<PortfolioLite> convertFromJson(String json) /*-{
		return eval(json);
	}-*/;

	private void loadUsingJson() {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET,
				GWT.getHostPageBaseURL() + "services/portfolios");
		
		rb.setCallback(new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() != 200 ) {
					Window.alert("unknown status code " + response.getStatusCode());
					return;
				}
				JsArray<PortfolioLite> portfolios = convertFromJson(response.getText());
				int row = 1;
				for(int i=0;i<portfolios.length();i++) {
					final PortfolioLite portfolio = portfolios.get(i);
					Anchor portfolioLink = new Anchor(portfolio.getName());
					portfolioLink.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							History.newItem("portfolio:id=" + portfolio.getId());
						}
					});
					portfolioList.setWidget(row, 0, portfolioLink);
					portfolioList.setWidget(row, 1, new Label(portfolio.getName()));
					row++;
				}				
			}
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("request failed " + exception.getMessage());				
			}
		});
		
		rb.setHeader("Accept", "application/json");
		
		try {
			rb.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
	
}
