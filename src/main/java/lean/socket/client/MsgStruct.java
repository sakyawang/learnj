package lean.socket.client;

public class MsgStruct {
	
	/**
	 * 数据的总长度
	 */
	private int len;
	
	/**
	 * 数据长度,的byte的数据量,默认是C语言的int,4byte
	 */
	private int lenByteCount = 4;
	
	/**
	 * 数据类型，指明数据是策略还是统计报告
	 */
	private int msg_type;
	
	/**
	 * 数据类型，的byte的数据量,默认是C语言的int,4byte
	 */
	private int msgTypeByteCount = 4;
	
	/**
	 * 真正的数据,也就是上述以JSON格式组织的数据
	 */
	private byte data[] = null;
	
	/**
	 * 返回结果异常
	 */
	private boolean isResult = false;

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getLenByteCount() {
		return lenByteCount;
	}

	public void setLenByteCount(int lenByteCount) {
		this.lenByteCount = lenByteCount;
	}

	public int getMsgTypeByteCount() {
		return msgTypeByteCount;
	}

	public void setMsgTypeByteCount(int msgTypeByteCount) {
		this.msgTypeByteCount = msgTypeByteCount;
	}
	
	public void result()
	{
		isResult = true;
	}
	
	public boolean isResult()
	{
		return isResult;
	}
	
}
