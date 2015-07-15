package mbpgate;

import mbpgate.domain.transactreponse.TransactResponseRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.JaxbHelper;
import utils.PropertyManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 同步Socket客户端
 */
public class MbpClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String MBP_TIMEOUT = PropertyManager.getProperty("socket.mbp.timeout");
    private static final String MBP_IP = PropertyManager.getProperty("socket.mbp.ip");
    private static final int MBP_PORT = PropertyManager.getIntProperty("socket.mbp.port");

    public byte[] onSend(byte[] msg) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {

       /* StringBuilder builder = new StringBuilder();
        builder.append(StringPad.rightPad4ChineseToByteLength(txnCode, 32, " "));
        builder.append(StringPad.rightPad4ChineseToByteLength(clientName, 16, " "));
        builder.append(StringPad.rightPad4ChineseToByteLength(bankCode, 16, " "));
        builder.append(StringPad.rightPad4ChineseToByteLength("" + msg.getBytes().length, 10, " "));
        builder.append(msg);*/

        Socket socket = new Socket(MBP_IP, MBP_PORT);
        socket.setSoTimeout(Integer.parseInt(MBP_TIMEOUT));
        OutputStream os = socket.getOutputStream();
        os.write(msg);
        os.flush();
        InputStream is = socket.getInputStream();
        byte[] headerBytes = new byte[74];
        is.read(headerBytes);
        byte[] lengthBytes = new byte[10];
        System.arraycopy(headerBytes, 64, lengthBytes, 0, 10);
        int toReadlength = Integer.parseInt(new String(lengthBytes).trim());
        byte[] dataBytes = new byte[toReadlength];

        int available = 0;
        int readIndex = 0;

        while (readIndex < toReadlength) {
            int toRead = 0;
            available = is.available();
            if (available == 0) continue;
            if (toReadlength - readIndex >= available) {
                toRead = available;
            } else {
                toRead = toReadlength - readIndex;
            }
            logger.info("toRead:" + toRead);
            byte[] buf = new byte[toRead];
            is.read(buf);
            System.arraycopy(buf, 0, dataBytes, readIndex, buf.length);
            readIndex += toRead;
        }
        /*byte[] msgbuf = new byte[74 + toReadlength];
        System.arraycopy(headerBytes, 0, msgbuf, 0, headerBytes.length);
        System.arraycopy(dataBytes, 0, msgbuf, headerBytes.length, dataBytes.length);*/
        return dataBytes;
    }

    public TransactResponseRoot convert4Transact(byte[] bytes) {
        JaxbHelper jaxbHelper = new JaxbHelper();
        TransactResponseRoot resBean = jaxbHelper.xmlToBean(TransactResponseRoot.class, bytes);
        logger.info("MBP Response:" + resBean);
        return resBean;
    }

}
