package exception;

/**
 * 未知实例化方法异常
 *
 * @author wangkaiping
 */
public class UnknownSerializerException extends RuntimeException {

    public byte serializerType;

    public UnknownSerializerException(byte serializerType) {
        super("Unknown serialization type: " + serializerType);
        this.serializerType = serializerType;
    }
}
