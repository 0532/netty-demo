package deposit;

import java.io.IOException;

/**
 * ���״���
 */
public interface TxnProcessor {
    String process(String userid, String msgData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException;
}
