package com.jgreenlight.core.script;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IDGenerator {
    private final Logger log =  LoggerFactory.getLogger(IDGenerator.class);
	
    private final String idFileLocation;
	

	public IDGenerator(String idFileLocation) {
		this.idFileLocation = idFileLocation;
	}


	public synchronized Long getNewID() {
		try {
			String runIdFileContent = FileUtils.readFileToString(new File(idFileLocation), Charset.defaultCharset());
			Long newRunId = Long.parseLong(runIdFileContent) + 1;
			FileUtils.write(new File(idFileLocation), newRunId.toString(), Charset.defaultCharset());
			log.info("New scriptRunID from file:" + newRunId);
			return newRunId;
		} catch (IOException ioe) {
			Long newRunId = System.currentTimeMillis();
			log.error("Failed to read scriptRunID from file:" + idFileLocation +". SystemId is used instead="+newRunId);
			return System.currentTimeMillis();
		}
	}
}
