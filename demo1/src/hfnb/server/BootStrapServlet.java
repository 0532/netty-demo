package hfnb.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plantform.utils.PropertyManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by Lichao.W At 2015/7/13 15:51
 * wanglichao@163.com
 */

public class BootStrapServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BootStrapServlet.class);

    @Override
    public void init() throws ServletException {
        try {
            // �������ض����������
            logger.info("HFNB ServerSocket����......");
            int portForStaring = PropertyManager.getIntProperty("ta.hfnb.server.socket.port");
            LocalServerBootstrap localServer = new LocalServerBootstrap(portForStaring);
            localServer.start();
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
