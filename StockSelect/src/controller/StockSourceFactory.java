package controller;

public class StockSourceFactory implements StockSourceFactoryInterface {
	/*
	 * (non-Javadoc)
	 * @see controller.StockSourceFactoryInterface#make(int)
	 * 1表示同花顺
	 * 2表示雪球
	 */
	@Override
	public CrawStocks make(int sourceOf) throws Exception {
		// TODO Auto-generated method stub
		if(sourceOf == 1){
			return new CrawStockTongHuaShun();
		}
		else if(sourceOf == 2){
			return new CrawStockXueQiu();
		}
		else{
			throw new Exception("股票数据来源序号错误！");
		}
	}

}
