package tenx.gwt.portfolio.client.rpc;

import java.util.logging.Level;
import java.util.logging.Logger;

import tenx.gwt.common.client.PaginationRequest;
import tenx.gwt.common.client.PaginationResult;
import tenx.gwt.portfolio.shared.PortfolioEntry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.extensions.gwtrpcds.GwtRpcDataSource;

public class PortfolioEntryDataSource extends GwtRpcDataSource {

	private static final Logger LOGGER = Logger.getLogger(PortfolioEntryDataSource.class.getName());
	
	PortfolioServiceAsync portfolioServiceAsync = GWT.create(PortfolioService.class);
	private Integer portfolioId;
	
	public static PortfolioEntryDataSource getInstance(Integer portfolioId) {
		return new PortfolioEntryDataSource(portfolioId);
	}

	static public final String GRID_FIELD_ID = "ID";
	static public final String GRID_FIELD_PROFIT_LOSS = "profitOrLoss";
	static public final String GRID_FIELD_MARKET_PRICE = "marketPrice";
	static public final String GRID_FIELD_PURCHASE_PRICE = "purchasePrice";
	static public final String GRID_FIELD_QUANTITY = "quantity";
	static public final String GRID_FIELD_SYMBOL = "symbol";

	public PortfolioEntryDataSource(Integer portfolioId) {
		this.portfolioId = portfolioId;
		
		DataSourceField idField = new DataSourceIntegerField(GRID_FIELD_ID);
		idField.setHidden(true);
		idField.setPrimaryKey(true);

		DataSourceFloatField marketPriceField = new DataSourceFloatField(
				GRID_FIELD_MARKET_PRICE, "Market Price");
		marketPriceField.setCanEdit(false);

		DataSourceFloatField profitLossField = new DataSourceFloatField(
				GRID_FIELD_PROFIT_LOSS, "Profit / Loss");
		profitLossField.setCanEdit(false);

		setFields(idField,
				new DataSourceTextField(GRID_FIELD_SYMBOL, "Symbol"),
				new DataSourceIntegerField(GRID_FIELD_QUANTITY, "Quantity"),
				new DataSourceFloatField(GRID_FIELD_PURCHASE_PRICE,
						"Purchase Price"), marketPriceField, profitLossField);
	}

	@Override
	protected void executeFetch(final String requestId, DSRequest request,
			final DSResponse response) {
		
		
		int startRow = request.getStartRow() == null  ? 0 : request.getStartRow();
		int endRow = request.getEndRow() == null ? startRow + 25 : request.getEndRow();
		
		SortSpecifier[] sortFields = request.getSortBy();
		String sortColumn = "symbol";
		boolean ascending = true;
		if (sortFields != null && sortFields.length > 0) {
			sortColumn = sortFields[0].getField();
			ascending = sortFields[0].getSortDirection() == SortDirection.ASCENDING;
		}
		
		
		PaginationRequest paginationRequest = new PaginationRequest(startRow,endRow);
		LOGGER.info("requesting server for data : " + requestId);
		LOGGER.log(Level.INFO,"Pagination request :\n"+paginationRequest);
		portfolioServiceAsync.getPortfolioEntries(portfolioId,sortColumn,ascending, paginationRequest, 
				new AsyncCallback<PaginationResult<PortfolioEntry>>() {
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.info("server call failed for " + requestId);
				response.setStatus (RPCResponse.STATUS_FAILURE);
                processResponse (requestId, response);
			}

			@Override
			public void onSuccess(PaginationResult<PortfolioEntry> result) {
				LOGGER.info("server call successful for " + requestId);
				LOGGER.log(Level.INFO,"returned :/n"+result);

				ListGridRecord[] records = new ListGridRecord[result.getCurrentPage().size()];
				int i = 0;
				for(PortfolioEntry portfolioEntry:result.getCurrentPage()) {
					records[i++] = convertToRecord(portfolioEntry);
				}
				response.setData(records);
				response.setTotalRows(result.getTotalSize());
				processResponse(requestId, response);
			}
		});

	}

	public static   ListGridRecord convertToRecord(PortfolioEntry portfolioEntry) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(GRID_FIELD_ID, portfolioEntry.getId());
		record.setAttribute(GRID_FIELD_SYMBOL,portfolioEntry.getSymbol());
		record.setAttribute(GRID_FIELD_QUANTITY,portfolioEntry.getQuantity());
		record.setAttribute(GRID_FIELD_PURCHASE_PRICE, portfolioEntry.getPurchasePrice());
		record.setAttribute(GRID_FIELD_MARKET_PRICE,portfolioEntry.getMarketPrice());
		record.setAttribute(GRID_FIELD_PROFIT_LOSS,portfolioEntry.getProfitOrLoss());
		return record;
	}


	@Override
	protected void executeAdd(String requestId, DSRequest request,
			DSResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void executeUpdate(String requestId, DSRequest request,
			DSResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void executeRemove(String requestId, DSRequest request,
			DSResponse response) {
		// TODO Auto-generated method stub

	}
}
