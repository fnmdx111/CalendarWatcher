package me.mad4a.calendarwatcher.data;


/**
 * User: chsc4698@gmail.com
 * Date: 12-3-15
 * all rights reserved
 */
public interface TraverseTransactor<T> {

	/**
	 * @param input do not change!!! readonly!!!
	 * @return transacted value
	 */
	public T transact(final Object input);

}
