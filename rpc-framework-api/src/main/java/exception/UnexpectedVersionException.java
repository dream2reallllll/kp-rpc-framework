package exception;

/**
 * 解析的RpcMessage版本有错
 *
 * @author wangkaiping
 */
public class UnexpectedVersionException extends RuntimeException {

    public UnexpectedVersionException() {
        super("Unknown version of Rpc Request.");
    }

}
