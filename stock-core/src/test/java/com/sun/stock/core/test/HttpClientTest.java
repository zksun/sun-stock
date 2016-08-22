package com.sun.stock.core.test;

import com.sun.stock.core.domain.information.ExcludeRights;
import com.sun.stock.core.informations.http.sohu.PathConstant;
import com.sun.stock.core.informations.http.sohu.SohuInfoExplorer;
import com.sun.stock.core.util.StockAlgorithmUtil;
import com.sun.stock.core.util.http.HttpGet;
import com.sun.stock.core.domain.enums.Timer;
import com.sun.stock.core.domain.information.KlineItem;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by zksun on 16-2-13.
 */
public class HttpClientTest {

	@Test
	public void getHttpResponse() {
		try {
			String httpStringResponse = HttpGet.getHttpGetInstance("www.sohu.com").getHttpStringResponse();
			System.out.println(httpStringResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getStockTradeInfoDetail() {
		String tradeDetailInfo = SohuInfoExplorer.getInstance().getTradeDetailInfo("002024");
		System.out.println(tradeDetailInfo);
	}

	@Test
	public void getStockRewardDetail() throws ParseException {
		List<ExcludeRights> excludeRightses = SohuInfoExplorer.getInstance().
				getExcludeRightsInfo("002024", Timer.DAY);


		System.out.println("OK");
	}

	@Test
	public void getStockTradedDetail() {
		List<KlineItem> klineInfo = SohuInfoExplorer.getInstance().getKlineInfo("000651", Timer.DAY, true, "20140228", -1);
		System.out.println("OK");
	}

	@Test
	public void getStockExcludeRightPrice() {
		List<ExcludeRights> excludeRightses = SohuInfoExplorer.getInstance().
				getExcludeRightsInfo("000651", Timer.DAY);

		Long aLong = StockAlgorithmUtil.exchangeAdjustStockPrice(excludeRightses, 2530L, "20150703");
		System.out.println(aLong);
	}


	@Test
	public void httpClientOrdinaryGet() throws IOException {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(PathConstant.MAIN_PATH + PathConstant.INFO_PATH + "?type=tradedetail");
		client.executeMethod(method);
		System.out.println(method.getStatusLine());
		String response = new String(method.getResponseBodyAsString().getBytes("8859_1"));

		System.out.println(response);
		method.releaseConnection();
	}

}
