package com.github.lucasgms.usermanagement.features.client.domain.valueObject;

import com.github.lucasgms.usermanagement.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Cnpj {
    @Column(name = "cnpj")
    private String value;

    public Cnpj() {}

    public Cnpj(String cnpj) {
        this.value = cnpj;

        if (!validateCnpj(cnpj)) {
            throw new BusinessException("Cnpj inválido: " + value);
        }
    }

    public static boolean validateCnpj(String cnpj) {
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
                cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
                cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
                cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
                cnpj.equals("88888888888888") || cnpj.equals("99999999999999") ||
                (cnpj.length() != 14))
            return (false);

        char dig13, dig14;
        int sm, i, r, num, wheight;

        sm = 0;
        wheight = 2;
        for (i = 11; i >= 0; i--) {
            num = (int) (cnpj.charAt(i) - 48);
            sm = sm + (num * wheight);
            wheight = wheight + 1;
            if (wheight == 10)
                wheight = 2;
        }

        r = sm % 11;
        if ((r == 0) || (r == 1))
            dig13 = '0';
        else dig13 = (char) ((11 - r) + 48);

        sm = 0;
        wheight = 2;
        for (i = 12; i >= 0; i--) {
            num = (int) (cnpj.charAt(i) - 48);
            sm = sm + (num * wheight);
            wheight = wheight + 1;
            if (wheight == 10)
                wheight = 2;
        }

        r = sm % 11;
        if ((r == 0) || (r == 1))
            dig14 = '0';
        else dig14 = (char) ((11 - r) + 48);

        if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
            return (true);
        else return (false);
    }

    public static String formatCnpj(String cnpj) {
        return (cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." +
                cnpj.substring(5, 8) + "." + cnpj.substring(8, 12) + "-" +
                cnpj.substring(12, 14));
    }

    public String getValue() {
        return value;
    }
}
