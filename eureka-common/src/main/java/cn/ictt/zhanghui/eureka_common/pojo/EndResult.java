package cn.ictt.zhanghui.eureka_common.pojo;

public class EndResult<T> {
	private Integer resultCode; // 200,500,404
	private T data;
	private String msg;

	public EndResult() {
	}

	public EndResult(Integer resultCode) {
		this.resultCode = resultCode;
	}


	public EndResult(Integer resultCode, String msg) {
		this.msg = msg;
		this.resultCode = resultCode;
	}

	public EndResult(Integer resultCode, String msg, T data) {
		this.msg = msg;
		this.resultCode = resultCode;
		this.data = data;
	}

	public static EndResult returnSuccess(){
		return new EndResult(ResultCode.SUCCESS.getCode());
	}

	public static EndResult returnFailure(){
		return new EndResult(ResultCode.SERVER_ERROR.getCode());
	}

	@Override
	public String toString() {
		return "EndResult{" +
				"resultCode=" + resultCode +
				", data=" + data +
				", msg='" + msg + '\'' +
				'}';
	}
}
