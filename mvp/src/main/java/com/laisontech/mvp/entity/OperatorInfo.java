package com.laisontech.mvp.entity;

/**
 * Created by SDP on 2018/4/13.
 */

public class OperatorInfo {
    //操作员UID
    private String operatoruid;
    //操作员状态
    private int state;
    //操作员账户余额密文
    private String balance;

    //自来水公司代码
    private int areacode;

    //自来水公司名称
    private String areaname;

    //自来水公司 站点代码
    private int stationcode;

    //自来水公司 站点名称
    private String stationname;
    //金额符号
    private String currencyunit;
    // laison表根密钥
    private String meterrootkey;
    //根密钥客户号
    private String customercode;
    //VAT模式
    private int vatmode;
    private String operatorpwd;

    @Override
    public String toString() {
        return "OperatorInfo{" +
                "operatoruid='" + operatoruid + '\'' +
                ", state=" + state +
                ", balance='" + balance + '\'' +
                ", areacode=" + areacode +
                ", areaname='" + areaname + '\'' +
                ", stationcode=" + stationcode +
                ", stationname='" + stationname + '\'' +
                ", currencyunit='" + currencyunit + '\'' +
                ", meterrootkey='" + meterrootkey + '\'' +
                ", customercode='" + customercode + '\'' +
                ", vatmode=" + vatmode +
                ", operatorpwd='" + operatorpwd + '\'' +
                '}';
    }
}
