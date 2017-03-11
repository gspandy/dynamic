package com.kanven.dynamic;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态数据源拦截器
 * 
 * @author kanven
 * @date 2016年5月23日 上午10:26:07
 */
public class DynamicDataSourceAdvice implements MethodInterceptor {
	
	private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceAdvice.class);

	private Map<String, String> prefixs = new HashMap<String, String>();

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		before(method);
		Object result = invocation.proceed();
		after();
		return result;
	}

	private void before(Method method) throws DynamicDataSourceException {
		String db = getDataSourceKey(method.getName());
		DynamicTracker tracker = DynamicDataSourceContextHolder.getTracker();
		if (isSelectDataSource(tracker)) {
			if (StringUtils.isNotEmpty(db)) {
				if (!db.equals(tracker.getDataSourceKey())) {
					throw new DynamicDataSourceException("数据源前后不一致，前：" + tracker.getDataSourceKey() + ",后：" + db);
				}
			}
		} else {
			if (StringUtils.isNotEmpty(db)) {
				tracker.setDataSourceKey(db);
			} else {
				tracker.setDataSourceKey(DynamicDataSourceContextHolder.DEFAULT_DATASOURCE_KEY);
			}
		}
		tracker.increase();
		DynamicDataSourceContextHolder.setTracker(tracker);
	}

	private void after() throws DynamicDataSourceException {
		DynamicTracker tracker = DynamicDataSourceContextHolder.getTracker();
		tracker.decrease();
		if (tracker.getCount() <= 0) {
			DynamicDataSourceContextHolder.clear();
			return ;
		}
		DynamicDataSourceContextHolder.setTracker(tracker);
	}

	private boolean isSelectDataSource(DynamicTracker tracker) {
		return StringUtils.isNotEmpty(tracker.getDataSourceKey());
	}

	private String getDataSourceKey(String methodName) {
		if(log.isDebugEnabled()){
			log.debug("线程编号：{},方法名:{}",Thread.currentThread().getId(),methodName);
		}
		String db = null;
		Set<String> keys = prefixs.keySet();
		for (String key : keys) {
			if (methodName.startsWith(key)) {
				db = prefixs.get(key);
				break;
			}
		}
		return db;
	}

	public void setPrefixs(Map<String, String> prefixs) {
		this.prefixs = prefixs;
	}

}
