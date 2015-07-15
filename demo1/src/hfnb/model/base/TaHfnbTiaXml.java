package hfnb.model.base;


import hfnb.enums.TxnRtnCode;

import java.io.Serializable;

/**
 * Created by Lichao.W At 2015/7/13 15:51
 * wanglichao@163.com
 */

public abstract class TaHfnbTiaXml implements Serializable {
     public TaHfnbTiaXml transform(String xml) {
         try {
         return getTia(xml);
         }catch (Exception e) {
             throw new RuntimeException(TxnRtnCode.MSG_ANALYSIS_ILLEGAL.toRtnMsg());
         }
     }

    public abstract TaHfnbTiaXml getTia(String xml);
}
