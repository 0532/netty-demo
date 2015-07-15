package servergate.serverstart;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servergate.enums.SBSFormCode;
import servergate.enums.TxnRtnCode;
import utils.MD5Helper;
import utils.PropertyManager;
import utils.StringPad;
import view.deposit.AbstractTxnProcessor;

/**
 * Created by Lichao.W At 2015/7/13 15:51
 * wanglichao@163.com
 * 服务端数据处理辅助类
 */

public class ServerChannelHandler extends SimpleChannelHandler {
    private static final Logger logger = LoggerFactory.getLogger(ServerChannelHandler.class);


    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent me) throws Exception {
        String rtnMsgHeader = null;
        String rtnMsgData = null;
        String txnCode = null;
        String txnDate = null;
        String userid = null;
        String userkey = null;
        try {
            byte[] bytes = (byte[]) me.getMessage();
            logger.info("【接收到报文】" + new String(bytes));
            // 解析报文头
            int headerLength = 94;
            byte[] headerBytes = new byte[headerLength];
            System.arraycopy(bytes, 8, headerBytes, 0, headerBytes.length);
            String msgHeader = new String(headerBytes, "GBK");
            rtnMsgHeader = msgHeader.substring(8, 38);

            // 报文体
            byte[] bodyBytes = new byte[bytes.length - headerLength-8];
            System.arraycopy(bytes, headerLength+8, bodyBytes, 0, bodyBytes.length);
            String msgData = new String(bodyBytes, "GBK");
            rtnMsgData = msgData;
            // 外围系统用户ID、交易码、交易日期、mac
            userid = msgHeader.substring(4, 14).trim();
            userkey = PropertyManager.getProperty("wsys.userkey." + userid);
            txnCode = msgHeader.substring(14, 24).trim();
            txnDate = msgHeader.substring(24, 32).trim();
            String txnTime = msgHeader.substring(32, 38).trim();
            String mac = new String(headerBytes, 62, 32);
            // MD5校验
            // MAC	32	以（Message Data部分 + TXN_DATE + USER_ID + USER_KEY）为依据产生的用ASC字符表示的16进制MD5值。其中USER_KEY由财务公司针对每个用户单独下发。
            String md5 = MD5Helper.getMD5String(msgData + txnDate + userid + userkey);
            // 验证失败 返回验证失败信息
            if (!md5.equalsIgnoreCase(mac)) {
                logger.info("用户标识：" + userid + " " + userkey + " 交易时间:" + txnDate + " " + txnTime);
                logger.info("MAC校验失败[服务端]MD5:" + md5 + "[客户端]MAC:" + mac);
                throw new RuntimeException(TxnRtnCode.MSG_VERIFY_MAC_ILLEGAL.toRtnMsg());
            } else {
                logger.info("MAC校验成功,用户标识：" + userid + " " + userkey + " 交易时间:" + txnDate + " " + txnTime);
            }
            String rtnXml = "";
            // 特殊交易特殊处理
            Class txnClass = null;
            try {
                txnClass = Class.forName("view.deposit.Txn" + txnCode + "Processor");
            } catch (ClassNotFoundException e) {
                txnClass = null;
                throw new RuntimeException(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.getCode() + "|" + TxnRtnCode.MSG_ANALYSIS_ILLEGAL.getTitle());
            }
            if (txnClass != null) {
                AbstractTxnProcessor processor = (AbstractTxnProcessor) txnClass.newInstance();
                rtnXml = processor.process(userid, msgData);
            }
            String rtnmac = MD5Helper.getMD5String(rtnXml + txnDate + userid + userkey);
            rtnMsgHeader = rtnMsgHeader + TxnRtnCode.TXN_PROCESSED.getCode()
                    + StringPad.rightPad4ChineseToByteLength(TxnRtnCode.TXN_PROCESSED.getTitle(), 20, " ")
                    + rtnmac;
            logger.info("【SbsSktRouteBuilder 发送报文】" + new String((rtnmac + "\n" + rtnXml).getBytes("GBK")));
            me.getChannel().write((rtnmac + "\n" + rtnMsgHeader).getBytes("GBK"));
        } catch (Exception e) {
            //  返回异常信息
            if (txnCode == null) {
                logger.error("报文解析失败，无法解析到交易码.", e);
                return;
            } else {
                String exmsg = e.getMessage();
                logger.error("交易异常", e);
                if (exmsg == null) {
                    exmsg = TxnRtnCode.SERVER_EXCEPTION.getCode() + "|" + TxnRtnCode.SERVER_EXCEPTION.getTitle();
                } else if (!exmsg.contains("|")) {
                    exmsg = TxnRtnCode.SERVER_EXCEPTION.getCode() + "|" + exmsg;
                }
                String errmsg[] = exmsg.split("\\|");
                SBSFormCode msgFormCode = SBSFormCode.valueOfAlias(errmsg[0]);
                if (msgFormCode != null) {
                    errmsg[1] = msgFormCode.getTitle();
                }
//                String rtnmsg = tiaToToa.run(rtnMsgData, errmsg[0], errmsg[1]);
//                String rtnmac = MD5Helper.getMD5String(rtnmsg + txnDate + userid + userkey);
//                rtnMsgHeader = rtnMsgHeader + errmsg[0]
//                        + StringPad.rightPad4ChineseToByteLength(errmsg[1], 20, " ")
//                        + rtnmac;
//                exchange.getOut().setBody((rtnMsgHeader + rtnmsg).getBytes());
//                me.getChannel().write((rtnmac + "\n" + rtnMsgHeader).getBytes("GBK"));
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
