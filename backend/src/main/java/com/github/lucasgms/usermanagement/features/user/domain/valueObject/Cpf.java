package com.github.lucasgms.usermanagement.features.user.domain.valueObject;

import com.github.lucasgms.usermanagement.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Cpf {
    @Column(name = "cpf")
    private String value;

    public Cpf() {}

    Cpf(String cpf) {
        this.value = cpf;

        if (!validateCpf(cpf)) {
            throw new BusinessException("CPF inválido: " + value);
        }

    }

    private boolean validateCpf(String valor) {
        if (valor == null || !valor.matches("\\d{11}")) {
            return false;
        }

        valor = valor.replaceAll("[^0-9]", "");

        if (valor.equals("00000000000") ||
                valor.equals("11111111111") ||
                valor.equals("22222222222") ||
                valor.equals("33333333333") ||
                valor.equals("44444444444") ||
                valor.equals("55555555555") ||
                valor.equals("66666666666") ||
                valor.equals("77777777777") ||
                valor.equals("88888888888") ||
                valor.equals("99999999999")) {
            return false;
        }

        int[] digits = new int[11];

        for (int i = 0; i < 11; i++) {
            digits[i] = Character.getNumericValue(valor.charAt(i));
        }

        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }

        int remainder = sum % 11;
        int checkDigit1 = (remainder < 2) ? 0 : 11 - remainder;

        if (digits[9] != checkDigit1) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digits[i] * (11 - i);
        }
        remainder = sum % 11;
        int checkDigit2 = (remainder < 2) ? 0 : 11 - remainder;

        if (digits[10] != checkDigit2) {
            return false;
        }

        return true;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cpf cpf = (Cpf) o;
        return Objects.equals(value, cpf.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
