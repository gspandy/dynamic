package com.kanven.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源切换上下文
 * 
 * @author kanven
 * @date 2016年5月23日 上午10:40:06
 */
class DynamicDataSourceContextHolder {

	private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

	private static final ThreadLocal<DynamicTracker> trackers = new ThreadLocal<DynamicTracker>() {
		@Override
		protected DynamicTracker initialValue() {
			DynamicTracker tracker = new DynamicTracker();
			tracker.setDataSourceKey("");
			return tracker;
		}
	};

	// 默认数据源键值
	static final String DEFAULT_DATASOURCE_KEY = "-default-";

	public static DynamicTracker getTracker() {
		DynamicTracker tracker = trackers.get();
		if (log.isDebugEnabled()) {
			log.debug("获取数据源的key：{}，访问量：{}", tracker.getDataSourceKey(), tracker.getCount());
		}
		return tracker;
	}

	public static void setTracker(DynamicTracker tracker) {
		if (log.isDebugEnabled()) {
			log.debug("设置数据源的key：{}，访问量：{}", tracker.getDataSourceKey(), tracker.getCount());
		}
		trackers.set(tracker);
	}

	public static void clear() {
		if (log.isDebugEnabled()) {
			DynamicTracker tracker = trackers.get();
			log.debug("移除数据源的key：{}，访问量：{}", tracker.getDataSourceKey(), tracker.getCount());
		}
		trackers.remove();
	}

}
