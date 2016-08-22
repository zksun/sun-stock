package com.sun.stock.core.test;

import com.sun.stock.core.informations.http.sohu.SohuInfoExplorer;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by zksun on 16-4-18.
 */
public class SohuInfoExplorerTest {

	@Test
	public void getSohuInfoExplorerTest() {
		try {
			Class<?> aClass = Class.forName(SohuInfoExplorer.class.getName());
			Method method = aClass.getMethod("getInstance");
			SohuInfoExplorer invoker = (SohuInfoExplorer) method.invoke(null);
			String tradeDetailInfo = invoker.getTradeDetailInfo("002024");
			System.out.println(tradeDetailInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
