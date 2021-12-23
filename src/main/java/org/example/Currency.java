package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Currency {
    // id
    private Integer id;

    // code of currency (840, 978, 643)
    private String Code;

    // code of currency (USD, RUB, EUR)
    private String Ccy;

    // name of currency in rus
    private String CcyNm_RU;

    // name of currency in uz
    private String CcyNm_UZ;

    // name of currency in uz
    private String CcyNm_UZC;

    // name of currency in en
    private String CcyNm_EN;

    // nominal
    private String Nominal;

    // rate
    private String Rate;

    // difference
    private String Diff;

    // date
    private String Date;
}
