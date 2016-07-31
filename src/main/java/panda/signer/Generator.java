package panda.signer;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Keypair generator.
 * 
 * @author koetter
 */
public class Generator {

	/** Key strength 1024 */
	private static final int KEY_BITS = 1024;

	/**
	 * TODO
	 * 
	 * @return TODO
	 */
	public final File generateKeypair(final String path) {
		final File selectedDirectory = new File(path);
		final String pathToKeyDirectory = selectedDirectory.getAbsolutePath();
		generateKeys(path);
		return selectedDirectory;
	}

	/**
	 * TODO
	 * 
	 * @return TODO
	 */
	public final File generateKeypair() {
		return generateKeypair("./");
	}

	/**
	 * Generate keypair
	 * 
	 * @param folder
	 *            folder for saving the keypair
	 */
	private void generateKeys(final String folder) {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(Utils.ALGORITHM);
			keyGen.initialize(KEY_BITS);
			final KeyPair pair = keyGen.generateKeyPair();

			final PrivateKey priv = pair.getPrivate();
			final PublicKey pub = pair.getPublic();

			final byte[] key = pub.getEncoded();
			keyWriter(folder + "/pub", key);

			byte[] key2 = priv.getEncoded();
			key2 = priv.getEncoded();
			keyWriter(folder + "/priv", key2);

			System.out.println("++ keys generated");
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes down a key.
	 * 
	 * @param path
	 *            the path for the key
	 * @param key
	 *            the key
	 */
	private void keyWriter(final String path, final byte[] key) {
		try (FileOutputStream keyfos = new FileOutputStream(path)) {
			keyfos.write(key);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
