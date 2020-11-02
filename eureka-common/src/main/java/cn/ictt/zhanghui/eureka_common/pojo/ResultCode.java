package cn.ictt.zhanghui.eureka_common.pojo;

/**
 * 操作结果返回码
 * @author ZhangHui
 * @date 2019/7/9
 */
public enum ResultCode {
    SUCCESS("执行正常", 200),
    SERVER_ERROR("服务器异常", 500),
    PARAM_INVALID("参数无效", 404);

    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private ResultCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    // 通过编码获取名称
    public static String getName(int code) {
        for (ResultCode o : values()) {
            if (o.getCode() == code) {
                return o.message;
            }
        }
        return "";
    }
}
