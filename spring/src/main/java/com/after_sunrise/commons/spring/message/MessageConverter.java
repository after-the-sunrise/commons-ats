package com.after_sunrise.commons.spring.message;

/**
 * @author takanori.takase
 */
public interface MessageConverter {

	String convert(String key, Object... args);

}
