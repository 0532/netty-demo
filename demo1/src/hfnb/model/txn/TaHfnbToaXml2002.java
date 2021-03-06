package hfnb.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import hfnb.model.base.TaHfnbToaXml;
import hfnb.model.base.TaHfnbToaXmlInfo;

import java.io.Serializable;

/**
 * Created by Lichao.W At 2015/7/13 15:51
 * wanglichao@163.com
 */

@XStreamAlias("root")
public class TaHfnbToaXml2002 extends TaHfnbToaXml {
    public TaHfnbToaXmlInfo info = new TaHfnbToaXmlInfo();
    public Body Body = new Body();

    public TaHfnbToaXml2002.Body getBody() {
        return Body;
    }

    public void setBody(TaHfnbToaXml2002.Body body) {
        Body = body;
    }

    public TaHfnbToaXmlInfo getInfo() {
        return info;
    }

    public void setInfo(TaHfnbToaXmlInfo info) {
        this.info = info;
    }

    public static class Body implements Serializable {
        public String rtncode;        //业务层面-返回代码
        public String rtnmsg;         //业务层面-返回信息
        public String accounttype;    //帐户类别 0：监管户
        public String tradeamt;       //交存金额
        public String accountcode;    //监管专户账号
        public String accountname;    //监管专户户名
        public String fdcserial;      //预售资金监管平台流水

        public String getRtncode() {
            return rtncode;
        }

        public void setRtncode(String rtncode) {
            this.rtncode = rtncode;
        }

        public String getRtnmsg() {
            return rtnmsg;
        }

        public void setRtnmsg(String rtnmsg) {
            this.rtnmsg = rtnmsg;
        }

        public String getAccounttype() {
            return accounttype;
        }

        public void setAccounttype(String accounttype) {
            this.accounttype = accounttype;
        }

        public String getTradeamt() {
            return tradeamt;
        }

        public void setTradeamt(String tradeamt) {
            this.tradeamt = tradeamt;
        }

        public String getAccountcode() {
            return accountcode;
        }

        public void setAccountcode(String accountcode) {
            this.accountcode = accountcode;
        }

        public String getAccountname() {
            return accountname;
        }

        public void setAccountname(String accountname) {
            this.accountname = accountname;
        }

        public String getFdcserial() {
            return fdcserial;
        }

        public void setFdcserial(String fdcserial) {
            this.fdcserial = fdcserial;
        }
    }

    @Override
    public String toString() {
        this.info.txncode = "2001";
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(TaHfnbToaXml2002.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static TaHfnbToaXml2002 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TaHfnbToaXml2002.class);
        return (TaHfnbToaXml2002) xs.fromXML(xml);
    }
}
