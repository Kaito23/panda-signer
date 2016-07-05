package panda.signer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * Reader for publickeys.
 * 
 * @author koetter
 */
public class PublickeyReader {

	/**
	 * Get a publickey object from a publickey-file.
	 * 
	 * @param filename
	 *            publickey file
	 * @return the publickey
	 * @throws Exception
	 *             when there is a problem
	 */
	public final PublicKey get(final String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(Utils.ALGORITHM);
		return kf.generatePublic(spec);
	}
}