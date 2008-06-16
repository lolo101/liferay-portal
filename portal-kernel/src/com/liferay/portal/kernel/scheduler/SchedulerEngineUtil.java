/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;

import java.util.Collection;
import java.util.Date;

/**
 * <a href="SchedulerEngineUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class SchedulerEngineUtil {

	public static Collection<SchedulerRequest> getScheduledJobs(
			String groupName)
		throws SchedulerException {

		return _instance._getScheduledJobs(groupName);
	}

	public static void init(SchedulerEngine defaultScheduler) {
		_instance._init(defaultScheduler);
	}

	public static void schedule(
			String groupName, String cronText, Date startDate, Date endDate,
			String description, String destinationName, String messageBody)
		throws SchedulerException {

		_instance._schedule(
			groupName, cronText, startDate, endDate, description,
			destinationName, messageBody);
	}

	public static void shutdown() throws SchedulerException {
		_instance._shutdown();
	}

	public static void unschedule(String jobName, String groupName)
		throws SchedulerException {

		_instance._unschedule(jobName, groupName);
	}

	private Collection<SchedulerRequest> _getScheduledJobs(String groupName)
		throws SchedulerException {

		return _messageBusScheduler.getScheduledJobs(groupName);
	}

	private void _init(SchedulerEngine messageBusScheduler) {
		_messageBusScheduler = messageBusScheduler;
	}

	private void _schedule(
			String groupName, String cronText, Date startDate, Date endDate,
			String description, String destinationName, String messageBody)
		throws SchedulerException {

		_messageBusScheduler.schedule(
			groupName, cronText, startDate, endDate, description,
			destinationName, messageBody);
	}

	private void _shutdown() throws SchedulerException {
		_messageBusScheduler.shutdown();
	}

	private void _unschedule(String jobName, String groupName)
		throws SchedulerException {

		_messageBusScheduler.unschedule(jobName, groupName);
	}

	private static SchedulerEngineUtil _instance = new SchedulerEngineUtil();

	private SchedulerEngine _messageBusScheduler;

}