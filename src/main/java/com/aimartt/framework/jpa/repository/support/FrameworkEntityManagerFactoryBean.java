package com.aimartt.framework.jpa.repository.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.google.common.collect.Lists;

/**
 * 扩展LocalContainerEntityManagerFactoryBean，增强packagesToScan的可配置性。
 * 支持默认框架的package扫描配置和自定义包的扫描配置。
 * @author zhangpu
 */
public class FrameworkEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean {
	
	private static final String PACKAGES_DELIMITER = ",";

	@Override
	public void setPackagesToScan(String... packagesToScan) {
		List<String> packages = Lists.newArrayList();
		for (String packageToScan : packagesToScan) {
			if (StringUtils.isBlank(packageToScan)) {
				continue;
			}
			if (StringUtils.contains(packageToScan, PACKAGES_DELIMITER)) {
				packages.addAll(Lists.newArrayList(StringUtils.split(packageToScan, PACKAGES_DELIMITER)));
			} else {
				packages.add(packageToScan);
			}
		}
		super.setPackagesToScan(packages.toArray(new String[] {}));
	}
	
}