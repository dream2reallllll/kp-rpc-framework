package exception;

/**
 * RpcMessage对象解析格式不对
 *
 * @author wangkaiping
 */
public class UnexpectedMessageTypeException extends RuntimeException {

    public UnexpectedMessageTypeException() {
        super("Wrong type Rpc Message.");
    }

}
