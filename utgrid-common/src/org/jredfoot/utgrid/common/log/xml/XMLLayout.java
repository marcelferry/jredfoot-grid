/*
 * Copyright (c) 2002-2006 Universidade Federal de Campina Grande
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jredfoot.utgrid.common.log.xml;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Class that determines the format of log messages
 */
public class XMLLayout extends Layout {

	public String format( LoggingEvent le ) {

		StringBuffer formattedXML = new StringBuffer();

		/** ********** setting date to string */
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis( le.timeStamp );

		StringBuffer dateStr = new StringBuffer( 26 ); // size of string, to be
														// faster
		dateStr.append( date.get( Calendar.YEAR ) + "/" );
		dateStr.append( (date.get( Calendar.MONTH ) + 1) + "/" ); // 0 is
																	// January
		dateStr.append( date.get( Calendar.DAY_OF_MONTH ) + " " );
		dateStr.append( date.get( Calendar.HOUR_OF_DAY ) + ":" );
		dateStr.append( date.get( Calendar.MINUTE ) + ":" );
		dateStr.append( date.get( Calendar.SECOND ) + ":" );
		dateStr.append( date.get( Calendar.MILLISECOND ) );

		/** *********** setting layout of LoggingEvent */
		LocationInfo locInfo = le.getLocationInformation();

		if ( le != null ) {
			formattedXML.append( "<ENTRY" );
			formattedXML.append( " Type=\"" + le.getLevel() + "\"" );
			formattedXML.append( " Time=\"" + dateStr + "\"" );
			formattedXML.append( " Reference=\"" + locInfo.getClassName() + "." + locInfo.getMethodName() + ":"
					+ locInfo.getLineNumber() + "\"" );
			formattedXML.append( "><![CDATA[" );
			formattedXML.append( le.getMessage() );
			formattedXML.append( "]]></ENTRY>\n" );
		}

		return formattedXML.toString();
	}


	public boolean ignoresThrowable() {

		return true;
	}


	public void activateOptions() {

		// do nothing
	}

}
