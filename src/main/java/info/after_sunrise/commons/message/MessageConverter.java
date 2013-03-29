package info.after_sunrise.commons.message;

/**
 * @author takanori.takase
 */
public interface MessageConverter {

	String convert(String key, Object... args);

}
