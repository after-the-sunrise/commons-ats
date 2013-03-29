package info.after_sunrise.commons.bean;

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
