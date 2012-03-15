package org.m4aongh.calendarwatcher.data;

import android.database.Cursor;
import android.os.Bundle;

/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public interface TraverseTransactor<T> {

	/**
	 *
	 * @param input do not change!!! readonly!!!
	 * @return transacted value
	 */
	public T transact(final Cursor input);

}
