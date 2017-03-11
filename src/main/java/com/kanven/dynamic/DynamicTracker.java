package com.kanven.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态数据源跟踪器
 * 
 * @author kanven
 * @date 2016年5月23日 上午11:07:40
 */
class DynamicTracker {

	private static final Logger log = LoggerFactory.getLogger(DynamicTracker.class);

	private String dataSourceKey = "";

	private int count = 0;

	public String getDataSourceKey() {
		return dataSourceKey;
	}

	public void setDataSourceKey(String dataSourceKey) {
		if (log.isDebugEnabled()) {
			log.debug("切换前{}，切换后{}", this.dataSourceKey, dataSourceKey);
		}
		this.dataSourceKey = dataSourceKey;
	}

	/**
	 * 计数器增加一
	 * 
	 * @return void
	 * @author kanven
	 * @date 2016年5月23日
	 */
	public void increase() {
		++this.count;
	}

	/**
	 * 计数器减一
	 * 
	 * @return void
	 * @author kanven
	 * @throws DynamicDataSourceException 
	 * @date 2016年5月23日
	 */
	public void decrease() throws DynamicDataSourceException {
		--this.count;
		if(this.count < 0) {
			throw new DynamicDataSourceException("计数器不能为负数！");
		}
	}

	public int getCount() {
		return count;
	}

}
