package view.deposit;

import java.io.IOException;

/**
 * Created by Lichao.W At 2015/7/13 15:51
 * wanglichao@163.com
 * 交易处理
 */

public interface TxnProcessor {
    String process(String userid, String msgData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException;
}
