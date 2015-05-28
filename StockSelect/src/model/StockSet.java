package model;

import org.json.JSONArray;

public class StockSet {
//	code, shortName, priceChangeRatio, curPrice, pe, dynamicPE, pb
	private String code; //股票代码
	private String shortNmae; //股票简称
	private double priceChangeRatio; //涨跌幅
	private double curPrice; //现价
	private double pe; //市盈率
	private double dynamicPE; //动态市盈率
	private double pb; //市净率
	
	private JSONArray stockArray;
	
	StockSet(JSONArray stockArray){
		this.stockArray = stockArray;
	}
	
}
