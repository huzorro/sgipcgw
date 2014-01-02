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
import org.duodo.sgip13c.message.DeliverRequestMessage;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
@Author(name = "huzorro(huzorro@gmail.com)")
@Version(version = 10000)
@PluginImplementation
public class SimpleDeliverRequestMessage implements SubmitMessageHandlerPlugin {
	
	@InjectPlugin
	public SubmitMessageServicePlugin submitMessageServicePlugin;

	@Override
	public void submit() throws Exception {
		for(int i = 0; i < 1; i++) {
			DeliverRequestMessage requestMessage = new DeliverRequestMessage(3000020017L);
			requestMessage.setChannelIds("20017");
			requestMessage.setMessagecontent("a呵呵");
			requestMessage.setSpnumber("10669501");
			requestMessage.setUsernumber("8613252300000");
			submitMessageServicePlugin.submit(requestMessage);
		}		
	}

}
