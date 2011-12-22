package tenx.gwt.portfolio.shared;


public class PortfolioEntry {
	private String symbol;
	private Integer quantity;
	private float purchasePrice;
	private float marketPrice;
	private float profitOrLoss;

	public PortfolioEntry(String symbol, Integer quantity,
			float purchasePrice, float marketPrice,
			float profitOrLoss) {
		super();
		this.symbol = symbol;
		this.quantity = quantity;
		this.purchasePrice = purchasePrice;
		this.marketPrice = marketPrice;
		this.profitOrLoss = profitOrLoss;
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
