/**
 * Copyright (c) 2005, KoLmafia development team
 * http://kolmafia.sourceforge.net/
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  [1] Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *  [2] Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in
 *      the documentation and/or other materials provided with the
 *      distribution.
 *  [3] Neither the name "KoLmafia development team" nor the names of
 *      its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written
 *      permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package net.sourceforge.kolmafia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.IOException;

import javax.swing.JEditorPane;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 * Holder for the BuffBot log (which should survive outside of
 * the BuffBot Frame
 */

public class BuffBotHome extends LimitedSizeChatBuffer
{

	private static final DateFormat logDF =
			DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

	private static final SimpleDateFormat logSDF = new SimpleDateFormat( "yyyyMMdd" );

	private KoLmafia client;
	private boolean isActive;

	/**
	 * Creates a new instance of <code>BuffBotHome</code>.
	 */

	public BuffBotHome(KoLmafia client)
	{
		super( "Buffbot Log" );
		this.client = client;
		initialize();
	}

	public void timeStampedLogEntry(String entry)
	{
		append(logDF.format(new Date()) + ": " + entry );
	}

	/**
	 * Appends the given message to the chat buffer.
	 * @param	message	The message to be appended to this <code>ChatBuffer</code>
	 */

	public void append( String message )
	{	super.append( message + System.getProperty( "line.separator" ) );
	if ( client instanceof KoLmafiaCLI )
		client.updateDisplay( client.ENABLED_STATE, message.replaceAll( "<.*?>", "" ) );
	}

	/**
	 * Create the <code>BuffBotLog</code> and its associated file, if
	 * they don't already exist.
	 */

	public void initialize()
	{
		String dayOfYear = logSDF.format(new Date());
		String characterName = client == null ? "" : client.getLoginName();
		String noExtensionName = characterName.replaceAll( "\\p{Punct}", "" ).replaceAll( " ", "_" ).toLowerCase();

		setActiveLogFile( KoLmafia.DATA_DIRECTORY + noExtensionName + "_BuffBot" + dayOfYear + ".html", noExtensionName, true );
		isActive = false;
	}

	/**
	 * De-initializes the log for the buff bot.
	 */

	public void deinitialize()
	{
		closeActiveLogFile();
		isActive = false;
	}

	public void setBuffBotActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public boolean isBuffBotActive()
	{
		return isActive;
	}
}
