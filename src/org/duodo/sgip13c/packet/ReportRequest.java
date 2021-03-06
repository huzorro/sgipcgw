/**
 * 
 */
package org.duodo.sgip13c.packet;

import org.duodo.netty3ext.packet.DataType;
import org.duodo.netty3ext.packet.PacketStructure;

/**
 * @author huzorro(huzorro@gmail.com)
 *
 */
public enum ReportRequest implements PacketStructure {
	SUBMITSEQUENCENUMBER(SgipDataType.UNSIGNEDINT, true, 12),
	REPORTTYPE(SgipDataType.UNSIGNEDINT, true, 1),
	USERNUMBER(SgipDataType.OCTERSTRING, true, 21),
	STATE(SgipDataType.UNSIGNEDINT, true, 1),
	ERRORCODE(SgipDataType.UNSIGNEDINT, true, 1),
	RESERVE(SgipDataType.OCTERSTRING, true, 8);
	
	private SgipDataType dataType;
    private boolean isFixFiledLength; 
    private int length;
    
    private ReportRequest(SgipDataType dataType, boolean isFixFiledLength, int length) {
    	this.length = length;
    	this.dataType = dataType;
    	this.isFixFiledLength = isFixFiledLength;
    }
	@Override
	public DataType getDataType() {
		return dataType;
	}

	@Override
	public boolean isFixFiledLength() {
		return isFixFiledLength;
	}

	@Override
	public boolean isFixPacketLength() {
		return true;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public int getBodyLength() {
		int bodyLength = 0;
		for(ReportRequest r : ReportRequest.values()) {
			bodyLength += r.getLength();
		}
		return bodyLength;
	}	
}
