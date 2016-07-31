package panda.signer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Reader for privatekeys.
 * 
 * @author koetter
 */
public class PrivatekeyReader {

	/**
	 * Get a private key from a file.
	 * 
	 * @param filename
	 *            the path to the private key file
	 * @return the privatekey from file
	 * @throws Exception
	 *             when there is a problem
	 */
	public final PrivateKey get(final String filename) throws Exception {
		final File f = new File(filename);
		final FileInputStream fis = new FileInputStream(f);
		final DataInputStream dis = new DataInputStream(fis);
		final byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();

		final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		final KeyFactory kf = KeyFactory.getInstance(Utils.ALGORITHM);
		return kf.generatePrivate(spec);
	}
}