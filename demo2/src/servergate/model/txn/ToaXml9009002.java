package servergate.model.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import servergate.model.base.ToaXml;
import servergate.model.base.ToaXmlInfo;

import java.io.Serializable;

/**
 * 2.2	单笔对外支付9009002
 */

@XStreamAlias("ROOT")
public class ToaXml9009002 extends ToaXml {
    public ToaXmlInfo INFO = new ToaXmlInfo();
    public Body BODY = new Body();

    public static class Body implements Serializable {
        public String CUSNAM;       // 户名
        public String TOTCNT;       // 总记录数
        public String CURCNT;       // 本包内记录数

        public String NBKMSG;
        public String VCHNUM;	// 凭证号
        public String SECNUM;	// 外围系统流水号
        public String TXNCDE;	// 交易代号
        public String TLRNUM;	// 柜员号
        public String VCHSET;	// 传票套号
        public String SETSEQ;	// 传票套内顺序号
        public String TXNDAT;   // 交易日期
        public String INTCUR;	// 币别代号
        public String TXNAMT;	// 交易金额
        public String ACTTY1;	// 付款账号帐户类型
        public String IPTAC1;	// 付款账号
        public String CUSID1;	// 付款账号客户号
        public String ACTNM1;	// 付款账号户名
        public String SHTSEQ;
        public String ACTTY2;	// 帐户类型2
        public String IPTAC2;	// 帐号2
        public String CUSID2;	// 客户号2
        public String ACTNM2;	// 帐户名称2
        public String DPTPRD;
        public String VALDAT;	// 起息日
        public String AUTTLR;	// 授权柜员
        public String ACTTYP1;	// 是否落地处理
        public String ACTTYP2;	// 帐户类型2

        public String getCUSNAM() {
            return CUSNAM;
        }

        public void setCUSNAM(String CUSNAM) {
            this.CUSNAM = CUSNAM;
        }

        public String getTOTCNT() {
            return TOTCNT;
        }

        public void setTOTCNT(String TOTCNT) {
            this.TOTCNT = TOTCNT;
        }

        public String getCURCNT() {
            return CURCNT;
        }

        public void setCURCNT(String CURCNT) {
            this.CURCNT = CURCNT;
        }

        public String getNBKMSG() {
            return NBKMSG;
        }

        public void setNBKMSG(String NBKMSG) {
            this.NBKMSG = NBKMSG;
        }

        public String getVCHNUM() {
            return VCHNUM;
        }

        public void setVCHNUM(String VCHNUM) {
            this.VCHNUM = VCHNUM;
        }

        public String getSECNUM() {
            return SECNUM;
        }

        public void setSECNUM(String SECNUM) {
            this.SECNUM = SECNUM;
        }

        public String getTXNCDE() {
            return TXNCDE;
        }

        public void setTXNCDE(String TXNCDE) {
            this.TXNCDE = TXNCDE;
        }

        public String getTLRNUM() {
            return TLRNUM;
        }

        public void setTLRNUM(String TLRNUM) {
            this.TLRNUM = TLRNUM;
        }

        public String getVCHSET() {
            return VCHSET;
        }

        public void setVCHSET(String VCHSET) {
            this.VCHSET = VCHSET;
        }

        public String getSETSEQ() {
            return SETSEQ;
        }

        public void setSETSEQ(String SETSEQ) {
            this.SETSEQ = SETSEQ;
        }

        public String getTXNDAT() {
            return TXNDAT;
        }

        public void setTXNDAT(String TXNDAT) {
            this.TXNDAT = TXNDAT;
        }

        public String getINTCUR() {
            return INTCUR;
        }

        public void setINTCUR(String INTCUR) {
            this.INTCUR = INTCUR;
        }

        public String getTXNAMT() {
            return TXNAMT;
        }

        public void setTXNAMT(String TXNAMT) {
            this.TXNAMT = TXNAMT;
        }

        public String getACTTY1() {
            return ACTTY1;
        }

        public void setACTTY1(String ACTTY1) {
            this.ACTTY1 = ACTTY1;
        }

        public String getIPTAC1() {
            return IPTAC1;
        }

        public void setIPTAC1(String IPTAC1) {
            this.IPTAC1 = IPTAC1;
        }

        public String getCUSID1() {
            return CUSID1;
        }

        public void setCUSID1(String CUSID1) {
            this.CUSID1 = CUSID1;
        }

        public String getACTNM1() {
            return ACTNM1;
        }

        public void setACTNM1(String ACTNM1) {
            this.ACTNM1 = ACTNM1;
        }

        public String getSHTSEQ() {
            return SHTSEQ;
        }

        public void setSHTSEQ(String SHTSEQ) {
            this.SHTSEQ = SHTSEQ;
        }

        public String getACTTY2() {
            return ACTTY2;
        }

        public void setACTTY2(String ACTTY2) {
            this.ACTTY2 = ACTTY2;
        }

        public String getIPTAC2() {
            return IPTAC2;
        }

        public void setIPTAC2(String IPTAC2) {
            this.IPTAC2 = IPTAC2;
        }

        public String getCUSID2() {
            return CUSID2;
        }

        public void setCUSID2(String CUSID2) {
            this.CUSID2 = CUSID2;
        }

        public String getACTNM2() {
            return ACTNM2;
        }

        public void setACTNM2(String ACTNM2) {
            this.ACTNM2 = ACTNM2;
        }

        public String getDPTPRD() {
            return DPTPRD;
        }

        public void setDPTPRD(String DPTPRD) {
            this.DPTPRD = DPTPRD;
        }

        public String getVALDAT() {
            return VALDAT;
        }

        public void setVALDAT(String VALDAT) {
            this.VALDAT = VALDAT;
        }

        public String getAUTTLR() {
            return AUTTLR;
        }

        public void setAUTTLR(String AUTTLR) {
            this.AUTTLR = AUTTLR;
        }

        public String getACTTYP1() {
            return ACTTYP1;
        }

        public void setACTTYP1(String ACTTYP1) {
            this.ACTTYP1 = ACTTYP1;
        }

        public String getACTTYP2() {
            return ACTTYP2;
        }

        public void setACTTYP2(String ACTTYP2) {
            this.ACTTYP2 = ACTTYP2;
        }
    }

    @Override
    public String toString() {
        this.INFO.TXN_CODE = "9009002";
        XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(ToaXml9009002.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }

    public static ToaXml9009002 getToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(ToaXml9009002.class);
        return (ToaXml9009002) xs.fromXML(xml);
    }
}
