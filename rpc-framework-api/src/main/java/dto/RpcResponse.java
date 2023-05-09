package dto;

import lombok.*;

import java.io.Serializable;


/**
 * The dto of Rpc response.
 *
 * @author wangkaiping
 */
@Setter
@Getter
@ToString
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = 1905122041950251278L;

    private static final byte INITIALIZATION = 0;

    private static final byte SUCCEED = 1;

    private static final byte FAILED = 2;

    public RpcResponse() {

    }

    /**
     * The status code. 0 means initialized, 1 means succeed and 2 means failed.
     */
    private byte code;
    /**
     * The status information.
     */
    private String msg;
    /**
     * The response data.
     */
    private Object data;

    public void succeed(Object data) {
        this.data = data;
        code = SUCCEED;
    }

    public void failed(String msg) {
        this.msg = msg;
        code = FAILED;
    }
}
