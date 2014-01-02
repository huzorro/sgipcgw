/**
 * 
 */
package org.duodo.sgip13c.plugin;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;
import net.xeoh.plugins.base.annotations.meta.Author;
import net.xeoh.plugins.base.annotations.meta.Version;

import org.duodo.netty3ext.plugin.SubmitMessageHandlerPlugin;
import org.duodo.netty3ext.plugin.SubmitMessageServicePlugin;
import org.duodo.netty3ext.util.SequenceNumber;
import org.duodo.sgip13c.message.ReportRequestMessage;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
@Author(name = "huzorro(huzorro@gmail.com)")
@Version(version = 10000)
@PluginImplementation
public class SimpleDeliverReportMessage implements
		SubmitMessageHandlerPlugin {
	@InjectPlugin
	public SubmitMessageServicePlugin submitMessageServicePlugin;
	@Override
	public void submit() throws Exception {
		for(int i = 0; i < 1; i++) {
			ReportRequestMessage requestMessage = new ReportRequestMessage(3000020017L);
			requestMessage.setChannelIds("20017");
			requestMessage.setSequenceNumber(new SequenceNumber(3000020017L, System.currentTimeMillis()));
			requestMessage.setUsernumber("8613252300000");
			requestMessage.setState((short) 0);
			requestMessage.setErrorcode((short) 0);
			submitMessageServicePlugin.submit(requestMessage);
		}
	}

}
