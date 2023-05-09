package exception;

/**
 * 用户查找的服务名称有误
 *
 * @author wangkaiping
 */
public class UnknownServiceException extends RuntimeException {

    public UnknownServiceException() {
        super("The service name is not exist.");
    }
}
