package utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class JaxbHelper {

    public <T> T xmlToBean(Class msgBeanClazz, byte[] buffer) {
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(buffer);
            JAXBContext jaxbContext = JAXBContext.newInstance(msgBeanClazz);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            T msg = (T) um.unmarshal(is);
            return msg;
        } catch (JAXBException e) {
            throw new RuntimeException("XML转Bean时出错.", e);
        }
    }

    public <T> String beanToXml(Class msgBeanClazz, T msgBean) {
        try {
            JAXBContext jc = JAXBContext.newInstance(msgBeanClazz);
            Marshaller ms = jc.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            ms.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            ms.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ms.marshal(msgBean, os);
            byte[] buffer = os.toByteArray();

            return new String(buffer, "GBK");
        } catch (JAXBException e) {
            throw new RuntimeException("转换XML时出错.", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("转换XML时出错.", e);
        }
    }
}
