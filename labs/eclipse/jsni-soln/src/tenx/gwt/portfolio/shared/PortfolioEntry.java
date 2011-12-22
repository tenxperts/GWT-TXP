package tenx.gwt.portfolio.shared;

@SuppressWarnings("serial")
public class PortfolioEntry extends BaseVO {
	private Integer id;
	private String symbol;
	private Integer quantity;
	private float purchasePrice;
	private float marketPrice;
	private float profitOrLoss;

	PortfolioEntry(){
	}
	
	public PortfolioEntry( String symbol, Integer quantity,
			float purchasePrice) {
		this.symbol = symbol;
		this.quantity = quantity;
		this.purchasePrice = purchasePrice;
		this.marketPrice = purchasePrice;
		this.profitOrLoss = 0;
	}
	public PortfolioEntry(Integer id, String symbol, Integer quantity,
			float purchasePrice, float marketPrice, float profitOrLoss) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.quantity = quantity;
		this.purchasePrice = purchasePrice;
		this.marketPrice = marketPrice;
		this.profitOrLoss = profitOrLoss;
	}

	public Integer getId() {
		return id;
	}

	public String getSymbol() {
		return symbol;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public float getPurchasePrice() {
		return purchasePrice;
	}

	public float getMarketPrice() {
		return marketPrice;
	}

	public float getProfitOrLoss() {
		return profitOrLoss;
	}

	
}
