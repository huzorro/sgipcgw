package org.duodo.sgip13c.service;

import java.util.List;

import org.duodo.netty3ext.global.DefaultGlobalVarsInitialize;
import org.duodo.netty3ext.global.GlobalVars;
import org.duodo.netty3ext.plugin.DefaultReceivedMsgPluginManagerService;
import org.duodo.netty3ext.plugin.DefaultSubmitMsgPluginManagerService;
import org.duodo.netty3ext.processor.MessageBindToChannelSubmitProcessor;
import org.duodo.netty3ext.service.MessageBindToChannelSubmitService;
import org.duodo.netty3ext.service.Service;
import org.duodo.netty3ext.service.manager.ClientServices;
import org.duodo.netty3ext.service.manager.DefaultSessionPoolWatchService;
import org.duodo.sgip13c.global.SgipGlobalVarsInitialize;

/**
 * 
 * @author huzorro(huzorro@gmail.com)
 */
public class SgipClientService implements ClientServices {
	private String configName;
	private static final DefaultGlobalVarsInitialize globalVarsInitialize = new SgipGlobalVarsInitialize();

	public SgipClientService() {
		this("sgipsession");
	}

	public SgipClientService(String configName) {
		this.configName = configName;
	}


	@Override
	public ClientServices downstreamGlobalVarsInit() throws Exception {
		return downstreamGlobalVarsInit(null);
	}

	@Override
	public ClientServices downstreamGlobalVarsInit(List<String> configList)
			throws Exception {
		globalVarsInitialize.downstreamSessionConfigInitialize(configList)
				.downstreamSessionPoolInitialize(configList)
				.downstreamMessageQueueInitialize(configList)
				.downstreamThreadPoolInitialize(configList)
				.downstreamClientBootstrapInitialize(configList)
				.downstreamMessagePluginManagerInitialize(configList);
		return this;
	}


	public Service downstreamServiceInit() {
		return downstreamServiceInit(null);
	}

	public Service downstreamServiceInit(List<String> configList) {
		return new SgipDownstreamClientService(
				GlobalVars.downstreamSessionConfigMap.get(configName),
				GlobalVars.clientBootstrapMap, GlobalVars.receiveMsgQueueMap,
				GlobalVars.responseMsgQueueMap, GlobalVars.deliverMsgQueueMap,
				GlobalVars.scheduleExecutorMap, GlobalVars.sessionPoolMap,
				GlobalVars.sessionFactoryMap,
				GlobalVars.downstreamServicesRunningList, configList);
	}



	public Service downstreamPoolWatchServiceInit() {
		return downstreamPoolWatchServiceInit(null);
	}

	public Service downstreamPoolWatchServiceInit(List<String> configList) {
		return new DefaultSessionPoolWatchService(GlobalVars.sessionPoolMap,
				GlobalVars.sessionFactoryMap,
				GlobalVars.downstreamSessionConfigMap.get(configName),
				GlobalVars.externalScheduleExecutorMap, configList);
	}


	@Override
	public Service downstreamDeliverServiceInit() {
		return downstreamDeliverServiceInit(null);
	}

	@Override
	public Service downstreamDeliverServiceInit(List<String> configList) {
		return new MessageBindToChannelSubmitService<MessageBindToChannelSubmitProcessor>(
				GlobalVars.sessionPoolMap,
				GlobalVars.deliverMsgQueueMap, 
				GlobalVars.reserveMsgQueueMap, 
				GlobalVars.executorServiceMap,
				GlobalVars.downstreamSessionConfigMap.get(configName), 
				false, 
				MessageBindToChannelSubmitProcessor.class,
				configList);
	}

	@Override
	public Service downstreamReserveDeliverServiceInit() {
		return downstreamReserveDeliverServiceInit(null);
	}

	@Override
	public Service downstreamReserveDeliverServiceInit(List<String> configList) {
		return new MessageBindToChannelSubmitService<MessageBindToChannelSubmitProcessor>(
				GlobalVars.sessionPoolMap,
				GlobalVars.reserveMsgQueueMap, 
				GlobalVars.reserveMsgQueueMap, 
				GlobalVars.executorServiceMap,
				GlobalVars.downstreamSessionConfigMap.get(configName), 
				false, 
				MessageBindToChannelSubmitProcessor.class,
				configList);
	}

	@Override
	public Service downstreamDeliverMsgPluginManagerServiceInit() {
		return downstreamDeliverMsgPluginManagerServiceInit(null);
	}

	@Override
	public Service downstreamDeliverMsgPluginManagerServiceInit(
			List<String> configList) {
		return new DefaultSubmitMsgPluginManagerService(
				GlobalVars.downstreamSessionConfigMap.get(configName), 
				GlobalVars.deliverMsgQueueMap,
				GlobalVars.executorServiceMap, 
				GlobalVars.pluginManagerUtilMap, 
				configList);
	}


	@Override
	public Service downstreamResponseMsgPluginManagerServiceInit() {
		return downstreamResponseMsgPluginManagerServiceInit(null);
	}

	@Override
	public Service downstreamResponseMsgPluginManagerServiceInit(
			List<String> configList) {
		return new DefaultReceivedMsgPluginManagerService(
				GlobalVars.downstreamSessionConfigMap.get(configName),
				GlobalVars.responseMsgQueueMap, 
				GlobalVars.executorServiceMap, 
				GlobalVars.pluginManagerUtilMap,
				configList);
	}


	
	@Override
	public ClientServices duplexstreamGlobalVarsInit() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientServices duplexstreamGlobalVarsInit(List<String> configList)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamServiceInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamServiceInit(List<String> configList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamPoolWatchServiceInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamPoolWatchServiceInit(List<String> configList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamDeliverServiceInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamDeliverServiceInit(List<String> configList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamReserveDeliverServiceInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamReserveDeliverServiceInit(List<String> configList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamResponseMsgPluginManagerServiceInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamResponseMsgPluginManagerServiceInit(
			List<String> configList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamReceiverMsgPluginManagerServiceInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamReceiverMsgPluginManagerServiceInit(
			List<String> configList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamDeliverMsgPluginManagerServiceInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service duplexstreamDeliverMsgPluginManagerServiceInit(
			List<String> configList) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public ClientServices upstreamGlobalVarsInit() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientServices upstreamGlobalVarsInit(List<String> configList)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service upstreamServiceInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service upstreamServiceInit(List<String> configList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service upstreamPoolWatchServiceInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service upstreamPoolWatchServiceInit(List<String> configList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service upstreamReceiverMsgPluginManagerServiceInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service upstreamReceiverMsgPluginManagerServiceInit(
			List<String> configList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
