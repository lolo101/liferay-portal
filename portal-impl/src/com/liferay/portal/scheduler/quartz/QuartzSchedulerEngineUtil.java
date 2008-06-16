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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SerialDestination;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;

import java.util.Collection;
import java.util.Date;

/**
 * <a href="QuartzSchedulerEngineUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 *
 */
public class QuartzSchedulerEngineUtil {

	public static Collection<SchedulerRequest> getScheduledJobs(
			String groupName)
		throws SchedulerException {

		return _engine.getScheduledJobs(groupName);
	}

	public static void init() throws SchedulerException {
		if (_engine != null) {
			return;
		}

		_engine = new QuartzSchedulerEngineImpl();

		_engine.start();

		Destination destination = new SerialDestination(
			DestinationNames.SCHEDULER);

		MessageBusUtil.addDestination(destination);

		MessageBusUtil.registerMessageListener(
			destination.getName(), new QuartzMessageListener());
	}

	public static void schedule(
			String groupName, String cronText, Date startDate, Date endDate,
			String description, String destination, String messageBody)
		throws SchedulerException {

		_engine.schedule(
			groupName, cronText, startDate, endDate, description, destination,
			messageBody);
	}

	public static void shutdown() throws SchedulerException {
		_engine.shutdown();
	}

	public static void unschedule(String jobName, String groupName)
		throws SchedulerException {

		_engine.unschedule(jobName, groupName);
	}

	private static SchedulerEngine _engine;

}