package view.deposit;

import hfnb.model.txn.TaHfnbTiaXml2011;
import hfnb.model.txn.TaHfnbToaXml2011;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Lichao.W At 2015/7/13 15:51
 * wanglichao@163.com
 * 交存冲正
 */

public class TaTxn2011Action extends AbstractTxnProcessor {
    private static Logger logger = LoggerFactory.getLogger(TaTxn2001Action.class);

    @Override
    public String process(String userid, String msgData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        TaHfnbTiaXml2011 tia = (TaHfnbTiaXml2011) (new TaHfnbTiaXml2011().getTia(msgData));
        TaHfnbToaXml2011 toa = new TaHfnbToaXml2011();
        copyTiaInfoToToa(tia,toa);
        try {
            //1.组织与SBS通讯与报文，并与SBS通讯

            //2.根据SBS返回信息，组织与房产中心通讯报文

            //3.根据房产中心返回信息，组织与网银通讯报文
            toa.getInfo().setRtncode("0000");
            toa.getInfo().setRtnmsg("交易成功");
        } catch (Exception e) {
            logger.error("交存验证交易异常.", e);
            toa.getInfo().setRtncode("1000");
            toa.getBody().setRtncode("1000");
            toa.getInfo().setRtnmsg("交易异常");
            toa.getBody().setRtnmsg("交易异常");
        }
        return toa.toString();
    }
}