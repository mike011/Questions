package ca.charland.questions.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Basic file utilities such as deleting a folder.
 * 
 * @author Michael
 */
public final class FileUtilities {

	/**
	 * The name of the filename in the properties fi.e.
	 */
	private static String FILENAME_KEY = "FILE";

	/**
	 * No constructor needed for this class.
	 */
	private FileUtilities() {

	}

	/**
	 * Deletes a folder and the files in it. Note: Does not delete folders in a folder.
	 * 
	 * @param directory
	 *            The name of the directory you want to delete.
	 */
	public static void deleteDirectory(final String directory) {
		final File file = new File("data/" + directory);
		if (file.exists()) {
			final File[] listfiles = file.listFiles();
			if (listfiles.length > 0) {
				for (final File f : listfiles) {
					// System.out.print("Trying to delete "+f.getAbsolutePath());
					f.delete();
					if (f.exists()) {
						System.out.println(" - Failed");
					} else {
						System.out.println(" - Passed");
					}
				}
			}

			file.delete();
			if (file.exists()) {
				System.out.println(" - Failed");
			} else {
				System.out.println(" - Passed");
			}
		}
	}

	/**
	 * Loads a properties file.
	 * 
	 * @param filename
	 *            The name of the properties file.
	 * @return The properties from the file.
	 */
	public static Properties loadPropertiesFile(final String filename) {
		// properties in the startup directory
		Properties props = new java.util.Properties();
		FileInputStream fis = null;
		try {
			fis = new java.io.FileInputStream(new java.io.File(filename));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Loading of file <" + filename + "> failed.");
		}
		try {
			props.load(fis);
		} catch (IOException e) {
			System.err.println("Load of properties from file <" + filename + "> failed.");
			e.printStackTrace();
		}

		// Add the full path to the file as a property.
		props.setProperty(FILENAME_KEY, filename);

		return props;
	}

	/**
	 * Loads all the properties in the given directory.
	 * 
	 * @param folderName
	 *            Where all the property files are.
	 * @return An array of property files.
	 */
	public static ArrayList<Properties> loadPropertyFiles(final String folderName) {
		final File folder = new File(folderName);
		if (!folder.exists()) {
			throw new RuntimeException("The folder named <" + folderName + "> does not exist.");
		}

		if (!folder.isDirectory()) {
			throw new RuntimeException("The folder named <" + folderName + "> is not a folder.");
		}

		String[] files = folder.list(new FilenameFilter() {

			@Override
			public boolean accept(final File dir, final String name) {
				if (name.endsWith(".properties")) {
					return true;
				}
				return false;
			}

		});
		if (files.length == 0) {
			throw new RuntimeException("There are no properties files in the folder named <" + folderName + ">.");
		}

		final ArrayList<Properties> props = new ArrayList<Properties>();
		for (final String filename : files) {
			props.add(FileUtilities.loadPropertiesFile(folderName + File.separator + filename));
		}
		return props;
	}

	/**
	 * Update the properties file.
	 * 
	 * @param props
	 *            The properties to write.
	 */
	public static void updatePropertyFiles(final Properties props) {
		final String filename = props.getProperty(FILENAME_KEY);
		try {
			props.store(new FileOutputStream(filename), null);
		} catch (IOException e) {
			throw new RuntimeException("The folder named <" + filename + "> could not be wrote too.");
		}
	}
}
