package panda.signer;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Keypair generator.
 * 
 * @author koetter
 */
public class Generator {

	/**
	 * TODO
	 * 
	 * @param stage TODO
	 * @return TODO
	 */
	public final File generateKeypair(final Stage stage) {
		// TODO remove fx components
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Zielordner wählen");
		File selectedDirectory = directoryChooser.showDialog(stage);

		if (selectedDirectory == null) {
			System.out.println("No Directory selected");
		} else {
			// TODO alert mit Hinweis!
			String pathToKeyDirectory = selectedDirectory.getAbsolutePath();
			System.out.println(pathToKeyDirectory);
			generateKeys(pathToKeyDirectory);
		}

		return selectedDirectory;
	}

	/**
	 * Generate keypair
	 * 
	 * @param folder folder for saving the keypair
	 */
	private void generateKeys(final String folder) {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(Utils.ALGORITHM);
			keyGen.initialize(KEY_BITS);
			KeyPair pair = keyGen.generateKeyPair();

			PrivateKey priv = pair.getPrivate();
			PublicKey pub = pair.getPublic();

			byte[] key = pub.getEncoded();
			keyWriter(folder + "/pub", key);

			byte[] key2 = priv.getEncoded();
			key2 = priv.getEncoded();
			keyWriter(folder + "/priv", key2);

			System.out.println("++ keys generated");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes down a key.
	 * 
	 * @param path the path for the key
	 * @param key the key
	 */
	private void keyWriter(final String path, final byte[] key) {
		try (FileOutputStream keyfos = new FileOutputStream(path)) {
			keyfos.write(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Key strength 1024 */
	private static final int KEY_BITS = 1024;
}
