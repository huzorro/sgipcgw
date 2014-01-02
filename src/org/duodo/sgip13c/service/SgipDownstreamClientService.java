package org.duodo.sgip13c.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import org.duodo.netty3ext.config.session.SessionConfig;
import org.duodo.netty3ext.factory.Factory;
import org.duodo.netty3ext.factory.session.DefaultClientSessionFactory;
import org.duodo.netty3ext.factory.tcp.NettyTcpClientFactory;
import org.duodo.netty3ext.future.QFuture;
import org.duodo.netty3ext.message.Message;
import org.duodo.netty3ext.pool.session.SessionPool;
import org.duodo.netty3ext.queue.BdbQueueMap;
import org.duodo.netty3ext.service.Service;
import org.duodo.netty3ext.session.Session;
import org.duodo.netty3ext.tcp.client.NettyTcpClient;
import org.duodo.sgip13c.factory.message.BindRequestFacotry;
import org.duodo.sgip13c.factory.pipeline.DownstreamClientChannelPipelineFactory;
import org.duodo.sgip13c.message.BindRequestMessage;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 * 
 */
public class SgipDownstreamClientService implements Service {
	private final Logger logger = LoggerFactory
			.getLogger(SgipDownstreamClientService.class);
	private Map<String, SessionConfig> configMap;
	private Map<SessionConfig, ClientBootstrap> clientBootstrapMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> receiveMsgQueueMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap;
	private Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap;
	private Map<SessionConfig, ScheduledExecutorService> scheduleExecutorMap;
	private Map<SessionConfig, SessionPool> sessionPoolMap;
	private Map<SessionConfig, Factory<Session>> sessionFactoryMap;
	private List<SessionConfig> downstreamServicesRunningList;
	private List<String> configList;

	public SgipDownstreamClientService(
			Map<String, SessionConfig> configMap,
			Map<SessionConfig, ClientBootstrap> clientBootstrapMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> receiveMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap,
			Map<SessionConfig, ScheduledExecutorService> scheduleExecutorMap,
			Map<SessionConfig, SessionPool> sessionPoolMap,
			Map<SessionConfig, Factory<Session>> sessionFactoryMap,
			List<SessionConfig> downstreamServicesRunningList) {
		this(configMap, clientBootstrapMap, receiveMsgQueueMap,
				responseMsgQueueMap, deliverMsgQueueMap, scheduleExecutorMap,
				sessionPoolMap, sessionFactoryMap,
				downstreamServicesRunningList, null);
	}

	public SgipDownstreamClientService(
			Map<String, SessionConfig> configMap,
			Map<SessionConfig, ClientBootstrap> clientBootstrapMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> receiveMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> responseMsgQueueMap,
			Map<Object, BdbQueueMap<Long, QFuture<Message>>> deliverMsgQueueMap,
			Map<SessionConfig, ScheduledExecutorService> scheduleExecutorMap,
			Map<SessionConfig, SessionPool> sessionPoolMap,
			Map<SessionConfig, Factory<Session>> sessionFactoryMap,
			List<SessionConfig> downstreamServicesRunningList,
			List<String> configList) {
		this.configMap = configMap;
		this.clientBootstrapMap = clientBootstrapMap;
		this.receiveMsgQueueMap = receiveMsgQueueMap;
		this.responseMsgQueueMap = responseMsgQueueMap;
		this.deliverMsgQueueMap = deliverMsgQueueMap;
		this.scheduleExecutorMap = scheduleExecutorMap;
		this.sessionPoolMap = sessionPoolMap;
		this.sessionFactoryMap = sessionFactoryMap;
		this.downstreamServicesRunningList = downstreamServicesRunningList;
		this.configList = configList;
	}

	@Override
	public void run() {
		try {
			process();
		} catch (Exception e) {
			logger.error("Cmpp Downstream Client Service failed {}", e);
			Runtime.getRuntime().exit(-1);
		}
	}

	@Override
	public void process() throws Exception {
		for (SessionConfig config : configMap.values()) {
			if (configList != null
					&& !configList.contains(config.getChannelIds()))
				continue;
			create(config);
			downstreamServicesRunningList.add(config);
		}
	}

	protected void create(SessionConfig config) throws Exception {
		ChannelPipelineFactory pipelineFactory = new DownstreamClientChannelPipelineFactory();
		NettyTcpClientFactory<NettyTcpClient<ChannelFuture>> tcpClientFactory = new NettyTcpClientFactory<NettyTcpClient<ChannelFuture>>(
				config.getHost(), config.getPort(), pipelineFactory,
				clientBootstrapMap.get(config));
		NettyTcpClient<ChannelFuture> tcpClient = tcpClientFactory.create();

		BindRequestFacotry<BindRequestMessage> bindRequestFacotry = new BindRequestFacotry<BindRequestMessage>(
				config);
		DefaultClientSessionFactory<Session> sessionFactory = new DefaultClientSessionFactory<Session>(
				tcpClient, bindRequestFacotry, config,
				deliverMsgQueueMap.get(config),
				responseMsgQueueMap.get(config),
				receiveMsgQueueMap.get(config),
				scheduleExecutorMap.get(config), sessionPoolMap.get(config));
		sessionFactoryMap.put(config, sessionFactory);
		for (int i = 0; i < config.getMaxSessions(); i++) {
			sessionFactory.create();
		}
	}
}
