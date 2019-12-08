package com.xiesx.gotv.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.Charsets;

import com.google.common.io.Files;

@Slf4j
@SuppressWarnings("all")
public class FileUtils {

	public static void fileWrite(final String fileName, final String contents) {
		checkNotNull(fileName, "Provided file name for writing must NOT be null.");
		checkNotNull(contents, "Unable to write null contents.");
		final File newFile = new File(fileName);
		try {
			Files.write(contents.getBytes(), newFile);
		} catch (Exception e) {
			log.error("ERROR trying to write to file '" + fileName + "' - " + e.toString());
		}
	}

	public static void fileAppend(final String fileName, final String contentsAppend) {
		checkNotNull(fileName, "Provided file name for writing must NOT be null.");
		checkNotNull(contentsAppend, "Unable to write null contents.");
		try {
			String contents_ys = demoFileRead(fileName);
			StringBuffer sBuffer = new StringBuffer(contents_ys);
			sBuffer.append(contentsAppend);
			fileWrite(fileName, sBuffer.toString());
		} catch (Exception e) {
			log.error("ERROR trying to append to file '" + fileName + "' - " + e.toString());
		}
	}

	public static String demoFileRead(final String testFilePath) throws IOException {
		File testFile = new File(testFilePath);
		return Files.toString(testFile, Charsets.UTF_8);
	}
}
