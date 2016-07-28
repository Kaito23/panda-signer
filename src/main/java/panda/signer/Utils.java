package panda.signer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Utils for airfield.
 * 
 * @author koetter
 */
public class Utils {

	/** Algorithm DSA or RSA */
	public static final String ALGORITHM = "DSA";
	/** SHA1withRSA || SHAwithDSA */
	public static final String SIGNATURE_ALGORITHM = "SHAwithDSA";

	/** 4096 */
	// private static final int BYTE_BUFFER = 4096;

	/**
	 * Get the bytes of a file.
	 * 
	 * @param file
	 *            the file
	 * @return the bytes of the file
	 * @throws IOException
	 *             TODO
	 */
	// private static byte[] getBytesFromFile(final File file) {
	// byte[] byteArray = null;
	// try (InputStream ios = new FileInputStream(file); ByteArrayOutputStream
	// ous = new ByteArrayOutputStream()) {
	// byte[] buffer = new byte[BYTE_BUFFER];
	// int read = 0;
	// while ((read = ios.read(buffer)) != -1) {
	// ous.write(buffer, 0, read);
	// }
	// byteArray = ous.toByteArray();
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return byteArray;
	// }

	/**
	 * TODO
	 * 
	 * @param fileList
	 *            TODO
	 * @return TODO
	 * @throws IOException
	 *             TODO
	 */
	// private static byte[] getBytesFromArrayList(final List<File> fileList)
	// throws IOException {
	// // TODO besser arrayList<String>?
	// byte[] yourBytes;
	//
	// try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
	// ObjectOutput out = new ObjectOutputStream(bos)) {
	// out.writeObject(fileList);
	// yourBytes = bos.toByteArray();
	// }
	//
	// return yourBytes;
	// }

	/**
	 * Adds files recursive to a list from a directory.
	 * 
	 * @param directoryName
	 *            TODO
	 * @param fileList
	 *            the list of files
	 */
	public static void listFiles(final String directoryName, final ArrayList<File> fileList) {
		final File directory = new File(directoryName);

		// get all the files from a directory
		final File[] fileArray = directory.listFiles();
		if (fileArray.length != 0 || fileArray != null) {
			for (final File file : fileArray) {
				if (file.isFile()) {
					if (!file.getName().startsWith(".") && !file.getName().startsWith("sig.properties")) {
						fileList.add(file);
					} else if (file.getName().startsWith(".")) {
						System.out.println("Ignoring " + file.getName());
					} else if (file.getName().equals("sig.properties")) {
						System.out.println("Ignoring sig.properties");
					}
				} else if (file.isDirectory() && !file.getName().startsWith(".")) {
					listFiles(file.getAbsolutePath(), fileList);
				} else if (file.getName().startsWith(".")) {
					System.out.println("Ignoring " + file.getName());
				}
			}
		}
	}

	/**
	 * Get hash of a file. (sha256hex)
	 * 
	 * @param file
	 *            the file
	 * @return has of the file
	 * @throws FileNotFoundException
	 *             TODO
	 * @throws IOException
	 *             TODO
	 * @throws NoSuchAlgorithmException
	 *             TODO
	 */
	public static final String getHashOfFile(final File file)
			throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		final FileInputStream fis = new FileInputStream(file);
		final String hash = DigestUtils.sha256Hex(fis);
		fis.close();
		return hash;
	}
}
