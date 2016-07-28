package panda.signer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GeneratorTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private final Generator generator = new Generator();

	@Test
	public void test() {
		generator.generateKeypair("C:\\");
	}

}
