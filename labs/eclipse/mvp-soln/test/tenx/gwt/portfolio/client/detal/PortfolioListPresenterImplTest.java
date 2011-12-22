package tenx.gwt.portfolio.client.detal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Test;

import tenx.gwt.portfolio.client.detail.PortfolioDetailsPresenter;
import tenx.gwt.portfolio.client.detail.PortfolioDetailsPresenterImpl;
import tenx.gwt.portfolio.client.detail.PortfolioDetailsView;
import tenx.gwt.portfolio.client.rpc.PortfolioServiceAsync;
import tenx.gwt.portfolio.shared.Portfolio;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

public class PortfolioListPresenterImplTest {

	@Test
	public void testRemoteCallSuccess() {
		
		final Portfolio portfolio = new Portfolio(1,"Short Term");
		
		HasText status = createMock(HasText.class);
		status.setText("Loading...");
		status.setText("Data as on " + portfolio.getAsOnDate());
		
		PortfolioServiceAsync portofolioService = createMock(PortfolioServiceAsync.class);
		portofolioService.getPortfolioDetail(eq(1),isA(AsyncCallback.class));
		
		expectLastCall().andAnswer(new IAnswer<Object>() {
			@Override
			public Object answer() throws Throwable {
				Object[] arguments = EasyMock.getCurrentArguments();
				((AsyncCallback)arguments[arguments.length-1]).onSuccess(portfolio);
				return null;
			}
			
		});
		
		PortfolioDetailsView portfolioDetailsView = createMock(PortfolioDetailsView.class);
		expect(portfolioDetailsView.getStatusBar()).andReturn(status);
		expectLastCall().times(2);
		portfolioDetailsView.setData(portfolio);
		
		replay(status,portofolioService,portfolioDetailsView);
		
		PortfolioDetailsPresenter presenter = new PortfolioDetailsPresenterImpl(1, portfolioDetailsView, portofolioService);
		presenter.onRefresh();
		
		verify(status,portfolioDetailsView,portofolioService);
	}

	
	@Test
	public void testRemoteCallFailureShouldSetStatus() {
		
		HasText status = createMock(HasText.class);
		status.setText("Loading...");
		status.setText("Loading failed due to server error");
		
		PortfolioServiceAsync portofolioService = createMock(PortfolioServiceAsync.class);
		portofolioService.getPortfolioDetail(eq(1),isA(AsyncCallback.class));
		
		expectLastCall().andAnswer(new IAnswer<Object>() {
			@Override
			public Object answer() throws Throwable {
				Object[] arguments = EasyMock.getCurrentArguments();
				((AsyncCallback)arguments[arguments.length-1]).onFailure(new Exception());
				return null;
			}
			
		});
		
		PortfolioDetailsView portfolioDetailsView = createMock(PortfolioDetailsView.class);
		expect(portfolioDetailsView.getStatusBar()).andReturn(status);
		expectLastCall().times(2);
		
		replay(status,portofolioService,portfolioDetailsView);
		
		PortfolioDetailsPresenter presenter = new PortfolioDetailsPresenterImpl(1, portfolioDetailsView, portofolioService);
		presenter.onRefresh();
		
		verify(status,portfolioDetailsView,portofolioService);
	}

}
