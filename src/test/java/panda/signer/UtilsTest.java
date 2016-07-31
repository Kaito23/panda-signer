package panda.signer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

/**
 * Test of utils.class
 * 
 * @author Robin
 */
public class UtilsTest {

	@Test
	public void testMd5Files() {
		final String path = UtilsTest.class.getResource("test.txt").getPath();
		final File file = new File(path);

		try {
			assertEquals(Utils.getHashOfFile(file), "ff4ef4245da5b09786e3d3de8b430292fa081984db272d2b13ed404b45353d28");
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}

}
