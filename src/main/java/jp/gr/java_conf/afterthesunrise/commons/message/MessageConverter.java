package jp.gr.java_conf.afterthesunrise.commons.message;

/**
 * @author takanori.takase
 */
public interface MessageConverter {

	String convert(String key, Object... args);

}
