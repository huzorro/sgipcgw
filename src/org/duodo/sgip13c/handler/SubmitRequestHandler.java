/**
 * 
 */
package org.duodo.sgip13c.handler;

import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.packet.PacketType;
import org.duodo.netty3ext.session.Session;
import org.duodo.sgip13c.message.SubmitRequestMessage;
import org.duodo.sgip13c.message.SubmitResponseMessage;
import org.duodo.sgip13c.packet.SgipPacketType;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class SubmitRequestHandler extends SimpleChannelUpstreamHandler {
	private PacketType packetType;

	public SubmitRequestHandler() {
		this(SgipPacketType.SUBMITREQUEST);
	}
	
	public SubmitRequestHandler(PacketType packetType) {
		this.packetType = packetType;
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		Message message = (Message) e.getMessage();
        long commandId = ((Long) message.getHeader().getCommandId()).longValue();
        if(commandId != packetType.getCommandId()){
            super.messageReceived(ctx, e);
            return;
        }
        
        SubmitRequestMessage requestMessage = (SubmitRequestMessage) message;
        SubmitResponseMessage responseMessage = new SubmitResponseMessage();
        
        responseMessage.setRequest(requestMessage);
        
        ctx.getChannel().write(responseMessage);
        
		((Session) ctx.getChannel().getAttachment()).receive(requestMessage
				.setResponse(responseMessage));
	}

	
}
