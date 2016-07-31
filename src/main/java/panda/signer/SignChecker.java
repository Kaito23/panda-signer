package panda.signer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;

/**
 * Checks all files in app dir so no corrupt files are in it.
 * 
 * @author koetter
 */
public class SignChecker {

	/** Path to the publickey */
	private String publickeyPath;

	/**
	 * Verifies the files.
	 * 
	 * @param publickeyPath
	 *            path to the publickey
	 * @param local
	 *            the path to the app directory
	 * @return true if files are correct
	 */
	public final boolean verify(final String publickeyPath, final String local) {
		this.publickeyPath = publickeyPath;
		boolean filesVerified = false;
		final Properties prop = new Properties();
		final String propertiesPath = local + File.separator + "sig.properties";
		try (InputStream input = new FileInputStream(propertiesPath)) {
			// load a properties file
			prop.load(input);
			// get the property value and print it out
			final String propertySignature = prop.getProperty("Signature");
			final ArrayList<File> fileList = new ArrayList<File>();
			Utils.listFiles(local, fileList);

			// byte[] bytesFromArrayList =
			// Utils.getBytesFromArrayList(fileList);
			// filesVerified = verify(bytesFromArrayList, propertySignature);

			String md5Strings = "";
			for (final File file : fileList) {
				try {
					md5Strings += Utils.getHashOfFile(file);
				} catch (final NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			filesVerified = verify(md5Strings.getBytes(), propertySignature);
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
		return filesVerified;
	}

	/**
	 * Verifies the files.
	 * 
	 * @param data
	 *            the data of all files from a filelist
	 * @param signature
	 *            the signature from properties
	 * @return true if files are verified correct
	 * @throws SignatureException
	 *             TODO
	 */
	private boolean verify(final byte[] data, final String signature) {
		boolean filesVerified = false;
		try {
			final Signature sign = Signature.getInstance(Utils.SIGNATURE_ALGORITHM);
			final PublickeyReader publicKeyReader = new PublickeyReader();
			final PublicKey publicKey = publicKeyReader.get(publickeyPath);
			sign.initVerify(publicKey);
			sign.update(data);
			filesVerified = sign.verify(Base64.decodeBase64(signature.getBytes("UTF-8")));
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return filesVerified;
	}

}
