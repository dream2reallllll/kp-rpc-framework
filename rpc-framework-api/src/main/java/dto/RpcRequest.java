package dto;


import lombok.*;

import java.io.Serializable;

/**
 * The transfer object of Rpc Request.
 *
 * @author wangkaiping
 * @date 2023.4.26
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private byte version;
    private String group;
    private String interfaceName;
    private int requestId;
    private Object[] parameters;
    private Class<?> paramsTypes;

    public String getRpcServiceName() {
        return interfaceName + "_" + group + "_" + version;
    }
}
