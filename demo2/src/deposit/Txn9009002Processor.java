package deposit;

import mbpgate.MbpClient;
import mbpgate.domain.ClientRequestHead;
import mbpgate.domain.transactreponse.TransactResponseRoot;
import mbpgate.domain.transactrequest.TransactRequestParam;
import mbpgate.domain.transactrequest.TransactRequestRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servergate.model.txn.TiaXml9009002;
import servergate.model.txn.ToaXml9009002;
import utils.JaxbHelper;
import utils.StringPad;

import java.io.IOException;

/**
 * 交易处理 SBS-MBP
 */
public class Txn9009002Processor extends AbstractTxnProcessor {

    private static Logger logger = LoggerFactory.getLogger(Txn9009002Processor.class);

    public String process(String userid, String msgData) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

        String rtnXml = "";
        // 先发起SBS交易
        //byte[] sbsReqMsg = new TiaXml9009002Transform().run(msgData, userid);
        TiaXml9009002 tia = (TiaXml9009002) (new TiaXml9009002().getTia(msgData));
        /*String termID = PropertyManager.getProperty("sbs.termid." + userid);
        if (StringUtils.isEmpty(termID)) {
            termID = "MT01";
        }
        byte[] sbsReqMsg = SbsTxnDataTransform.convertToTxnN120(tia, termID);
        // SBS
        byte[] bytes = new JmsBytesClient().sendRecivMsg("900", "fcdep", "fcdep", "9009002", userid,
                "queue.dep.core.fcdep.sbs", "queue.dep.core.sbs.fcdep", sbsReqMsg);
        // 解析SBS报文
        FebResponse response = new FebResponse();
        response.init(bytes);
        String formCode = response.getFormCodes().get(0);
        logger.info("SBS交易返回码：" + formCode);*/
        // bean -> txn bean
        ToaXml9009002 toa = new ToaXml9009002();
//        toa.INFO.RET_CODE = formCode;
        // TODO 不落地时，发往MBP
        String mbpMsg = assembleMbpMsg(tia);
        // TODO 直连第三方MBP
        MbpClient client = new MbpClient();
        byte[] rtnBytes = client.onSend(mbpMsg.getBytes());
        TransactResponseRoot res = client.convert4Transact(rtnBytes);
        // TODO
//        copyFormBodyToToa(t531, toa);
        /*if ("T531".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "交易完成";
            SOFForm form = response.getSofForms().get(0);
            T531 t531 = (T531) form.getFormBody();
            // 落地则直接返回
            if ("01".equals(t531.getACTTYP1())) {
                copyFormBodyToToa(t531, toa);
            } else {
                // TODO 不落地时，发往MBP
                String mbpMsg = assembleMbpMsg(tia);
                // TODO 直连第三方MBP
                MbpClient client = new MbpClient();
                byte[] rtnBytes = client.onSend(mbpMsg.getBytes());
                TransactResponseRoot res = client.convert4Transact(rtnBytes);
                // TODO
                copyFormBodyToToa(t531, toa);
            }
        } else if ("T999".equalsIgnoreCase(formCode)) {
            toa.INFO.RET_MSG = "交易完成";
            SOFForm form = response.getSofForms().get(0);
            T999 t999 = (T999) form.getFormBody();
            if ("01".equals(t999.getACTTYP1())) {
                copyFormBodyToToa(t999, toa);
            } else {
                // TODO 不落地时，发往MBP
                String mbpMsg = assembleMbpMsg(tia);
                logger.info(mbpMsg);
                MbpClient client = new MbpClient();
                byte[] rtnBytes = client.onSend(mbpMsg.getBytes());
                TransactResponseRoot res = client.convert4Transact(rtnBytes);
                // TODO
                copyFormBodyToToa(t999, toa);
            }
        } else {
            toa.INFO.RET_MSG = SBSFormCode.valueOfAlias(formCode).getTitle();
            if (StringUtils.isEmpty(toa.INFO.RET_MSG)) {
                toa.INFO.RET_MSG = "交易失败";
            }
        }
        return toa.toString();*/
        return null;
    }

    private String assembleMbpMsg(TiaXml9009002 tia) {

        StringBuilder builder = new StringBuilder();
        builder.append(StringPad.rightPad4ChineseToByteLength("Transact", 32, " "));
        builder.append(StringPad.rightPad4ChineseToByteLength("SBS", 16, " "));
        builder.append(StringPad.rightPad4ChineseToByteLength("105", 16, " "));

        // TODO 拼接报文
        String xml = "";
        TransactRequestRoot clientReqBean = new TransactRequestRoot();
        ClientRequestHead clientReqHead = new ClientRequestHead();
        TransactRequestParam clientReqParam = new TransactRequestParam();
        clientReqBean.setHead(clientReqHead);
        clientReqBean.setParam(clientReqParam);
        // TODO Head内变量赋值
        clientReqHead.setOpBankCode("105");
        clientReqHead.setOpName("Transacts");
        clientReqHead.setOpName("Transacts");
        clientReqHead.setOpEntID("SBS");
//        clientReqHead.setOpDate(tia.BODY.);

       /*
        public String ORGIDT;     // 交易机构
        public String FBTACT;     // 客户号
        public String ORDTYP;     // 交易类型
        public String RMTTYP;	  // 汇款类型
        public String CUSTYP;	  // 汇款帐户类型
        public String FEETYP;	  // 是否见单
        public String FEEACT;	  // 费用帐户
        public String PBKACT;	  // 人行账号
        public String CHQTYP;	  // 支票类型
        public String CHQPWD;	  // 支票密码
        public String FUNCDE;	  // 保留项
        public String ADVNUM;	  // FS流水号
        */

        clientReqParam.setToAccount(tia.BODY.BENACT);
        clientReqParam.setToName(tia.BODY.BENNAM);
        clientReqParam.setToBank(tia.BODY.BENBNK);
        clientReqParam.setReserved1(tia.BODY.CHQNUM);         // 转入账户内部行号
        clientReqParam.setReserved2(tia.BODY.CHQNUM);         // 转入账户12位行号

        clientReqParam.setFromAccount(tia.BODY.CUSACT);
        clientReqParam.setFromName(tia.BODY.RETNAM);
        clientReqParam.setFromBank(tia.BODY.AGENBK);          // 转出行
        clientReqParam.setEnterpriseSerial(tia.BODY.REQNUM);  // 请求序列号
        clientReqParam.setAmount(tia.BODY.TXNAMT);            // 金额
        clientReqParam.setCurrency(tia.BODY.TXNCUR);          // 币种
        clientReqParam.setUsage(tia.BODY.RETAUX);             // 用途
        clientReqParam.setTransDate(tia.BODY.ORDDAT);         // 交易日期

        clientReqParam.setSystem("1");


        // TODO

        return new JaxbHelper().beanToXml(TransactRequestRoot.class, clientReqBean);
    }


}
