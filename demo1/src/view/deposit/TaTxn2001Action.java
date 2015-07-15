package view.deposit;

import hfnb.model.txn.TaHfnbTiaXml2001;
import hfnb.model.txn.TaHfnbToaXml2001;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Lichao.W At 2015/7/13 15:51
 * wanglichao@163.com
 * ������֤
 */

public class TaTxn2001Action extends AbstractTxnProcessor {
    private static Logger logger = LoggerFactory.getLogger(TaTxn2001Action.class);

    @Override
    public String process(String userid, String msgData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        TaHfnbTiaXml2001 tia = (TaHfnbTiaXml2001) (new TaHfnbTiaXml2001().getTia(msgData));
        TaHfnbToaXml2001 toa = new TaHfnbToaXml2001();
        copyTiaInfoToToa(tia,toa);
        try {
//            toa.getInfo().setTxncode();
            //1.��֯�뷿������ͨѶ���ģ����뷿������ͨѶ

            //2.���ݷ������ķ�����Ϣ����֯������ͨѶ����
            toa.getInfo().setRtncode("0000");
            toa.getInfo().setRtnmsg("���׳ɹ�");
        } catch (Exception e) {
            logger.error("������֤�����쳣.", e);
            toa.getInfo().setRtncode("1000");
            toa.getBody().setRtncode("1000");
            toa.getInfo().setRtnmsg("�����쳣");
            toa.getBody().setRtnmsg("�����쳣");
        }
        return toa.toString();
    }
}
