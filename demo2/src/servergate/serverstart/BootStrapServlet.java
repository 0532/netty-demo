package servergate.serverstart;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PropertyManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by Lichao.W At 2015/7/14 9:35
 * wanglichao@163.com
 */
public class BootStrapServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(BootStrapServlet.class);
    private static int SBS_SOCKET_PORT = PropertyManager.getIntProperty("test.server.socket.port");

    @Override
    public void init() throws ServletException {
        try {
            // �������ض����������
            logger.info("HFNB ServerSocket����......");
            ServerBootstrap server = new ServerBootstrap(SBS_SOCKET_PORT);
            server.start();
            logger.info("HFNB ServerSocket�������......");
        } catch (Exception e) {
            logger.error("HFNB ServerSocket��ʼ������", e);
            System.exit(-1);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        System.exit(-1);
    }
}
