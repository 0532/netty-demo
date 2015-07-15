package servergate.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import servergate.model.base.TiaXml;
import servergate.model.base.TiaXmlInfo;

import java.io.Serializable;

/**
 *  单笔对外支付 SBS-MBP-建行
 */

@XStreamAlias("ROOT")
public class TiaXml9009002 extends TiaXml {
    public TiaXmlInfo INFO;
    public Body BODY;

    public static class Body implements Serializable {

        public String ORGIDT;     // 交易机构
        public String ORDDAT;     // 委托日期
        public String FBTACT;     // 客户号
        public String ORDTYP;     // 交易类型
        public String TXNCUR;     // 交易货币
        public String TXNAMT;	  // 交易金额
        public String RMTTYP;	  // 汇款类型
        public String CUSTYP;	  // 汇款帐户类型
        public String CUSACT;	  // 汇款帐户
        public String FEETYP;	  // 是否见单
        public String FEEACT;	  // 费用帐户
        public String BENACT;	  // 收款人帐号
        public String BENNAM;	  // 收款人名称
        public String BENBNK;	  // 收款行行名
        public String AGENBK;	  // 代理行行名
        public String PBKACT;	  // 人行账号
        public String RETNAM;	  // 汇款人名称
        public String RETAUX;	  // 汇款用途
        public String CHQTYP;	  // 支票类型
        public String CHQNUM;	  // 收款行行号
        public String CHQPWD;	  // 支票密码
        public String FUNCDE;	  // 保留项
        public String ADVNUM;	  // FS流水号
        public String REQNUM;	  // 交易流水号

    }

    @Override
    public TiaXml getTia(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TiaXml9009002.class);
        return (TiaXml9009002) xs.fromXML(xml);
    }
}
