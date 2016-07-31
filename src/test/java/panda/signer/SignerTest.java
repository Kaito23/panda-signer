package panda.signer;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test of signer class.
 * 
 * @author Robin
 */
public class SignerTest {

	private final Signer signer = new Signer();

	@Test
	public final void testCreateSignFile() {
		final String ppkPath = UtilsTest.class.getResource("priv").getPath();
		final String path = UtilsTest.class.getResource("folder").getPath();
		signer.createSignFile(ppkPath, path);

		final SignChecker signChecker = new SignChecker();
		final boolean verify = signChecker.verify(UtilsTest.class.getResource("pub").getPath(),
				UtilsTest.class.getResource("folder").getPath());
		assertTrue(verify);
	}

}
