/**
 * 
 */
package org.duodo.sgip13c.factory.pipeline;

import org.duodo.sgip13c.decoder.BindResponseDecoder;
import org.duodo.sgip13c.decoder.DeliverResponseDecoder;
import org.duodo.sgip13c.decoder.HeaderDecoder;
import org.duodo.sgip13c.decoder.ReportResponseDecoder;
import org.duodo.sgip13c.decoder.SubmitResponseDecoder;
import org.duodo.sgip13c.encoder.BindRequestEncoder;
import org.duodo.sgip13c.encoder.CommonsHeaderEncoder;
import org.duodo.sgip13c.encoder.DeliverRequestEncoder;
import org.duodo.sgip13c.encoder.DeliverRequestHeaderEncoder;
import org.duodo.sgip13c.encoder.HeaderEncoder;
import org.duodo.sgip13c.encoder.ReportRequestEncoder;
import org.duodo.sgip13c.encoder.SubmitRequestEncoder;
import org.duodo.sgip13c.encoder.SubmitRequestHeaderEncoder;
import org.duodo.sgip13c.handler.BindResponseHandler;
import org.duodo.sgip13c.handler.CommonsMessageHandler;
import org.duodo.sgip13c.handler.DeliverResponseHandler;
import org.duodo.sgip13c.handler.ReportResponseHandler;
import org.duodo.sgip13c.handler.SubmitResponseHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public class DownstreamClientChannelPipelineFactory implements
		ChannelPipelineFactory {

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();

		
		pipeline.addLast("HeaderDecoder", new HeaderDecoder());
		
		pipeline.addLast("BindResponseDecoder", new BindResponseDecoder());
		pipeline.addLast("BindRequestEncoder", new BindRequestEncoder());
		
		pipeline.addLast("SubmitResponseDecoder", new SubmitResponseDecoder());
		pipeline.addLast("SubmitRequestEncoder", new SubmitRequestEncoder());
		
		pipeline.addLast("DeliverResponseDecoder", new DeliverResponseDecoder());
		pipeline.addLast("DeliverRequestEncoder", new DeliverRequestEncoder());

		pipeline.addLast("ReportResponseDecoder", new ReportResponseDecoder());
		pipeline.addLast("ReportRequestEncoder", new ReportRequestEncoder());
		
		pipeline.addLast("HeaderEncoder", new HeaderEncoder());
		pipeline.addLast("DeliverRequestHeaderEncoder", new DeliverRequestHeaderEncoder());
		pipeline.addLast("SubmitRequestHeaderEncoder", new SubmitRequestHeaderEncoder());
		pipeline.addLast("CommonsHeaderEncoder", new CommonsHeaderEncoder());
		pipeline.addLast("CommonsMessageHandler", new CommonsMessageHandler());
		
		pipeline.addLast("BindResponseHandler", new BindResponseHandler());
		pipeline.addLast("SubmitResponseHandler", new SubmitResponseHandler());
		pipeline.addLast("DeliverResponseHandler", new DeliverResponseHandler());
		pipeline.addLast("ReportResponseHandler", new ReportResponseHandler());
		
		return pipeline;
	}

}
