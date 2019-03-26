package com.sxt.dto;

import com.sxt.pojo.BasicData;

/**
 * BasicData 传输对象
 * @author dengp
 *
 */
public class BasicDto extends BasePage{

	private BasicData basicData;

	public BasicData getBasicData() {
		return basicData;
	}

	public void setBasicData(BasicData basicData) {
		this.basicData = basicData;
	}
}
