package view.deposit;

import hfnb.model.txn.TaHfnbTiaXml2011;
import hfnb.model.txn.TaHfnbToaXml2011;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Lichao.W At 2015/7/13 15:51
 * wanglichao@163.com
 * �������
 */

public class TaTxn2011Action extends AbstractTxnProcessor {
    private static Logger logger = LoggerFactory.getLogger(TaTxn2001Action.class);

    @Override
    public String process(String userid, String msgData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        TaHfnbTiaXml2011 tia = (TaHfnbTiaXml2011) (new TaHfnbTiaXml2011().getTia(msgData));
        TaHfnbToaXml2011 toa = new TaHfnbToaXml2011();
        copyTiaInfoToToa(tia,toa);
        try {
            //1.��֯��SBSͨѶ�뱨�ģ�����SBSͨѶ

            //2.����SBS������Ϣ����֯�뷿������ͨѶ����

            //3.���ݷ������ķ�����Ϣ����֯������ͨѶ����
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