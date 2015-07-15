package deposit;


import sbsgate.domain.SOFFormBody;
import servergate.model.base.ToaXml;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 交易处理
 */
public abstract class AbstractTxnProcessor implements TxnProcessor {
    public abstract String process(String userid, String msgData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException;

    protected void copyFormBodyToToa(SOFFormBody formBody, ToaXml toa) {
        try {
            Field[] fields = formBody.getClass().getFields();
            Class toaCLass = toa.getClass();
            Object obj = null;
            for (Field field : fields) {
                obj = field.get(formBody);
                if (obj != null) {
                    Field toaField = toaCLass.getField(field.getName());
                    if (toaField != null) {
                        toaField.set(toa, obj);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("TxnProcessor copyFormBodyToToa 解析异常");
        }
    }
}
