package controller;

public interface StockSourceFactoryInterface {
	public CrawStocks make(int sourceOf) throws Exception;
}
