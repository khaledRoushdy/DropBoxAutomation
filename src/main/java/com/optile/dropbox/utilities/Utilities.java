package com.optile.dropbox.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWriteMode;

public class Utilities {

	public static void uploadFile(String fileName, String fileLocation,DbxClient client) throws DbxException, IOException {
		File inputFile = new File(fileName);
		FileInputStream inputStream = new FileInputStream(inputFile);
		try {

			DbxEntry.File uploadedFile = client.uploadFile(fileLocation, DbxWriteMode.add(), inputFile.length(),
					inputStream);
			System.out.println("Uploaded: " + uploadedFile.toString());
		} finally {
			inputStream.close();
		}
	}
	
	public static String generateString(Random rng, String characters, int length)
	{
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}

}
