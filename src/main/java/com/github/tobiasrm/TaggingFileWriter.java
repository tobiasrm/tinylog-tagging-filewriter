package com.github.tobiasrm;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.Set;

import org.pmw.tinylog.Configuration;
import org.pmw.tinylog.EnvironmentHelper;
import org.pmw.tinylog.LogEntry;
import org.pmw.tinylog.writers.LogEntryValue;
import org.pmw.tinylog.writers.PropertiesSupport;
import org.pmw.tinylog.writers.Property;
import org.pmw.tinylog.writers.VMShutdownHook;
import org.pmw.tinylog.writers.Writer;


/*
 * Copyright 2012 Martin Winandy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */


/**  Extension of the the tinylog ConsoleWriter enabling to print the level specified in the config.
 * @author Tobias R. Mayer (tobiasrm@me.com)
 * @since 2017-11
 */
@PropertiesSupport( name = "tagging-filewriter", properties = { 
		@Property(name = "filename",   type = String.class),
		@Property(name = "buffered",   type = boolean.class, optional = true), 
		@Property(name = "append",     type = boolean.class, optional = true),
		@Property(name = "customTag1", type = String.class, optional = true),
		@Property(name = "customTag2", type = String.class, optional = true),
		@Property(name = "customTag3", type = String.class, optional = true),
		@Property(name = "customTag4", type = String.class, optional = true),
		@Property(name = "customTag5", type = String.class, optional = true),
		@Property(name = "customTag6", type = String.class, optional = true),
		@Property(name = "customTag7", type = String.class, optional = true),
		@Property(name = "customTag8", type = String.class, optional = true),
		@Property(name = "customTag9", type = String.class, optional = true),
		@Property(name = "customTag10", type = String.class, optional = true),
		@Property(name = "customParam1", type = String.class, optional = true),
		@Property(name = "customParam2", type = String.class, optional = true),
		@Property(name = "customParam3", type = String.class, optional = true),
		@Property(name = "customParam4", type = String.class, optional = true),
		@Property(name = "customParam5", type = String.class, optional = true),
		@Property(name = "customParam6", type = String.class, optional = true),
		@Property(name = "customParam7", type = String.class, optional = true),
		@Property(name = "customParam8", type = String.class, optional = true),
		@Property(name = "customParam9", type = String.class, optional = true),
		@Property(name = "customParam10", type = String.class, optional = true)
})
public final class TaggingFileWriter implements Writer {

	private static final int BUFFER_SIZE = 64 * 1024;

	private final String filename;
	private final boolean buffered;
	private final boolean append;
	private OutputStream stream;


	// --------------------------------------------------------------------

	private final String customTag1;
	private final String customTag2;
	private final String customTag3;
	private final String customTag4;
	private final String customTag5;
	private final String customTag6;
	private final String customTag7;
	private final String customTag8;
	private final String customTag9;
	private final String customTag10;

	private final String customParam1;
	private final String customParam2;
	private final String customParam3;
	private final String customParam4;
	private final String customParam5;
	private final String customParam6;
	private final String customParam7;
	private final String customParam8;
	private final String customParam9;
	private final String customParam10;

	String l;

	// --------------------------------------------------------------------


	/**
	 * @param filename
	 *            Filename of the log file
	 */
	public TaggingFileWriter(final String filename) {
		this( filename, false, false, 
				null,null,null,null,null,null,null,null,null,null,
				null,null,null,null,null,null,null,null,null,null);
	}

	/**
	 * @param filename
	 *            Filename of the log file
	 * @param buffered
	 *            Buffered writing
	 */
	public TaggingFileWriter(final String filename, final boolean buffered) {
		this(filename, buffered, false,
				null,null,null,null,null,null,null,null,null,null,
				null,null,null,null,null,null,null,null,null,null);
	}

	//	/**
	//	 * @param filename
	//	 *            Filename of the log file
	//	 * @param buffered
	//	 *            Buffered writing
	//	 * @param append
	//	 *            Continuing existing file
	//	 */
	//	public TaggingFileWriter(final String filename, final boolean buffered, final boolean append) {
	//		this.filename = PathResolver.resolve(filename);
	//		this.buffered = buffered;
	//		this.append = append;
	//	}

	/**
	 * Helper constructor with wrapper class parameters for {@link org.pmw.tinylog.PropertiesLoader PropertiesLoader}.
	 * 
	 * @param filename
	 *            Filename of the log file
	 * @param buffered
	 *            Buffered writing
	 * @param append
	 *            Continuing existing file
	 */
	TaggingFileWriter(final String filename, final Boolean buffered, final Boolean append,
			final String customTag1, 
			final String customTag2, 
			final String customTag3, 
			final String customTag4, 
			final String customTag5, 
			final String customTag6, 
			final String customTag7, 
			final String customTag8, 
			final String customTag9, 
			final String customTag10,
			final String customParam1, 
			final String customParam2, 
			final String customParam3, 
			final String customParam4, 
			final String customParam5, 
			final String customParam6, 
			final String customParam7, 
			final String customParam8, 
			final String customParam9, 
			final String customParam10) {
		this.filename = filename;
		this.buffered = buffered == null ? false : buffered;
		this.append = append == null ? false : append;

		this.customTag1 = customTag1;
		this.customTag2 = customTag2;
		this.customTag3 = customTag3;
		this.customTag4 = customTag4;
		this.customTag5 = customTag5;
		this.customTag6 = customTag6;
		this.customTag7 = customTag7;
		this.customTag8 = customTag8;
		this.customTag9 = customTag9;
		this.customTag10 = customTag10;

		this.customParam1 = customParam1;
		this.customParam2 = customParam2;
		this.customParam3 = customParam3;
		this.customParam4 = customParam4;
		this.customParam5 = customParam5;
		this.customParam6 = customParam6;
		this.customParam7 = customParam7;
		this.customParam8 = customParam8;
		this.customParam9 = customParam9;
		this.customParam10 = customParam10;
	}

	//	@Override
	public Set<LogEntryValue> getRequiredLogEntryValues() {
		return EnumSet.of(LogEntryValue.RENDERED_LOG_ENTRY);
	}

	/**
	 * Get the filename of the log file.
	 *
	 * @return Filename of the log file
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Determine whether buffered writing is enabled.
	 *
	 * @return <code>true</code> if buffered writing is enabled, otherwise <code>false</code>
	 */
	public boolean isBuffered() {
		return buffered;
	}

	/**
	 * Determine whether appending is enabled.
	 *
	 * @return <code>true</code> if appending is enabled, otherwise <code>false</code>
	 */
	public boolean isAppending() {
		return append;
	}

	//	@Override
	public void init(final Configuration configuration) throws IOException {
		File file = new File(filename);
		EnvironmentHelper.makeDirectories(file);

		if (buffered) {
			stream = new BufferedOutputStream(new FileOutputStream(file, append), BUFFER_SIZE);
		} else {
			stream = new FileOutputStream(file, append);
		}

		VMShutdownHook.register(this);
	}

	//	@Override
	public void write(final LogEntry logEntry) throws IOException {
		l  = logEntry.getRenderedLogEntry();

		if( customTag1   != null) l = l.replace(customTag1,   ( customParam1   == null  ? "" : customParam1) );
		if( customTag2   != null) l = l.replace(customTag2,   ( customParam2   == null  ? "" : customParam2) );
		if( customTag3   != null) l = l.replace(customTag3,   ( customParam3   == null  ? "" : customParam3) );
		if( customTag4   != null) l = l.replace(customTag4,   ( customParam4   == null  ? "" : customParam4) );
		if( customTag5   != null) l = l.replace(customTag5,   ( customParam5   == null  ? "" : customParam5) );
		if( customTag6   != null) l = l.replace(customTag6,   ( customParam6   == null  ? "" : customParam6) );
		if( customTag7   != null) l = l.replace(customTag7,   ( customParam7   == null  ? "" : customParam7) );
		if( customTag8   != null) l = l.replace(customTag8,   ( customParam8   == null  ? "" : customParam8) );
		if( customTag9   != null) l = l.replace(customTag9,   ( customParam9   == null  ? "" : customParam9) );
		if( customTag10  != null) l = l.replace(customTag10,  ( customParam10  == null  ? "" : customParam10) );

		byte[] data = l.getBytes();
		synchronized (stream) {
			stream.write(data);
		}
	}

	//	@Override
	public void flush() throws IOException {
		if (buffered) {
			synchronized (stream) {
				stream.flush();
			}
		}
	}

	/**
	 * Close the log file.
	 *
	 * @throws IOException
	 *             Failed to close the log file
	 */
	//	@Override
	public void close() throws IOException {
		synchronized (stream) {
			VMShutdownHook.unregister(this);
			stream.close();
		}
	}

}
