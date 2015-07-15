package sbsgate.core;


import sbsgate.domain.SOFForm;

import java.util.ArrayList;
import java.util.List;

/**
 * SBS响应
 */
public class FebResponse {

    private int iCommareaSize = 32000;

    protected List<String> formCodes = new ArrayList<String>();
    protected List<SOFForm> sofForms = new ArrayList<SOFForm>();

    public List<String> getFormCodes() {
        return formCodes;
    }

    public List<SOFForm> getSofForms() {
        return sofForms;
    }

    // 解析报文
    public void init(byte[] buffer) {

        byte[] databytes = truncBuffer(buffer);
        byte[] resbytes = new byte[databytes.length + 4];
        System.arraycopy(databytes, 0, resbytes, 0, databytes.length);
        // 去掉了SBS报文为空的部分，保险起见加上4个空字节
        resbytes[databytes.length] = 0x00;
        resbytes[databytes.length + 1] = 0x00;
        resbytes[databytes.length + 2] = 0x00;
        resbytes[databytes.length + 3] = 0x00;

        int index = 0;
        do {
            // 包不是以空格开头，则视为空包，不解析
            if (buffer[index] != 0x20) {
                return;
            }
            SOFForm sofForm = new SOFForm();
            sofForm.assembleFields(index, buffer);
            formCodes.add(sofForm.getFormHeader().getFormCode());
            sofForms.add(sofForm);
            index += sofForm.length;
        } while (buffer.length > index);
    }

    private byte[] truncBuffer(byte[] buffer) {
        int count = 0;
        for (int i = 0; i < iCommareaSize; i++) {
            if (buffer[iCommareaSize - 1 - i] == 0x00) {
                count++;
            } else {
                break;
            }
        }
        byte[] outBuffer = new byte[iCommareaSize - count];
        System.arraycopy(buffer, 0, outBuffer, 0, outBuffer.length);
        return outBuffer;
    }
}
