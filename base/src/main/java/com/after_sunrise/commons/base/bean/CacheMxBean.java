package com.after_sunrise.commons.base.bean;

import javax.management.MXBean;

/**
 * @author takanori.takase
 */
@MXBean
public interface CacheMxBean {

	void load();

	void clear();

	void refresh();

}
