package info.after_sunrise.commons.object.spi;

/**
 * @author takanori.takase
 */
public class TestServiceImpl implements TestService {

	@Override
	public String sayHello() {
		return "Hello World!";
	}

}
