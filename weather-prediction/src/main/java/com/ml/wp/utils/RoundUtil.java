package com.ml.wp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class RoundUtil {

	public static Double getDoubleWith3Decimals(double d) {
		return new BigDecimal(d).setScale(3, RoundingMode.HALF_UP).doubleValue();
	}

}
