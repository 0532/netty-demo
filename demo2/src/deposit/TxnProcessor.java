package deposit;

import java.io.IOException;

/**
 * 交易处理
 */
public interface TxnProcessor {
    String process(String userid, String msgData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException;
}
