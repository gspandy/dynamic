package com.kanven.dynamic;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * 
 * @author kanven
 * @date 2016年5月23日 上午10:14:11
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

	private Set<Object> lookupKeys;

	@Override
	protected Object determineCurrentLookupKey() {
		DynamicTracker tracker = DynamicDataSourceContextHolder.getTracker();
		String key = tracker.getDataSourceKey();
		if (StringUtils.isNotEmpty(key) && !DynamicDataSourceContextHolder.DEFAULT_DATASOURCE_KEY.equals(key)) {
			if (lookupKeys.contains(key)) {
				if (log.isDebugEnabled()) {
					log.debug("使用{}数据源", key);
				}
				return key;
			}
			log.error("不存在{}对应的数据源,使用默认数据源！", key);
			return null;
		}
		if (log.isDebugEnabled()) {
			log.debug("使用{}默认数据源", key);
		}
		return null;
	}

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		super.setTargetDataSources(targetDataSources);
		lookupKeys = targetDataSources.keySet();
	}

}
