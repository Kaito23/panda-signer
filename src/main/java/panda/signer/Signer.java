package panda.signer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;

/**
 * Signer for applications. Creates the sig.properties and verifies the
 * downloaded content.
 * 
 * @author koetter
 */
public class Signer {

	/** Path to privatekey */
	private String privateKeyPath;

	/**
	 * Creates a sig.properties file in the app dir.
	 * 
	 * @param local the path to the app dir
	 * @param privatekeyFolderPath TODO
	 * @param publickeyFolderPath TODO
	 */
	public final void createSignFile(final String privatekeyFolderPath, final String publickeyFolderPath, final String local) {
		this.privateKeyPath = privatekeyFolderPath;
		System.out.println("create sigfile");

		Properties prop = new Properties();
		ArrayList<File> fileList = null;
		String sigFilePath = local + "/sig.properties";
		try (OutputStream output = new FileOutputStream(sigFilePath)) {
			fileList = new ArrayList<File>();
			Utils.listFiles(local, fileList);

			String signaturecombination = "";
			// set the properties value
			for (File file : fileList) {
				String fileName = file.getName();
				// byte[] bytesFromFile = Utils.getBytesFromFile(file);
				String md5Checksum;
				try {
					md5Checksum = Utils.getHashOfFile(file);
					prop.setProperty(fileName, md5Checksum);
					signaturecombination += md5Checksum;
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// byte[] byteList = Utils.getBytesFromArrayList(fileList);
			// String signature = createSignature(byteList);

			String signature = createSignature(signaturecombination.getBytes());
			prop.setProperty("Signature", signature);

			// save properties to project root folder
			prop.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	/**
	 * Get the signature of a file.
	 * 
	 * @param file the file as byte array
	 * @return the signature to the overgiven file
	 */
	final String createSignature(final byte[] file) {
		byte[] byteArraySignature = null;
		try {
			PrivatekeyReader privateKeyReader = new PrivatekeyReader();
			PrivateKey privateKey = privateKeyReader.get(privateKeyPath);
			Signature signature = Signature.getInstance(Utils.SIGNATURE_ALGORITHM);
			signature.initSign(privateKey);
			signature.update(file, 0, file.length);
			byteArraySignature = signature.sign();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] encryptedByteValue = Base64.encodeBase64(byteArraySignature);
		String returner;
		returner = new String(encryptedByteValue, DEFAULT_CHARSET);
		return returner;
	}

	/** Default charset utf-8 */
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

}
