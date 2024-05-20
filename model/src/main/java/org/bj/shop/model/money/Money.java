package org.bj.shop.model.money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public record Money(Currency currency, BigDecimal amount) {

    public Money {
        Objects.requireNonNull(currency, "Currency must not be null");
        Objects.requireNonNull(amount, "Amount must not be null");
        if (amount.scale() > currency.getDefaultFractionDigits()) {
            throw new IllegalArgumentException("Scale of amount %s is greater than the number of fraction digits used with currency %s"
                    .formatted(amount, currency));
        }
    }

    public static Money of(Currency currency, int mayor, int minor) {
        int scale = currency.getDefaultFractionDigits();
        return new Money(
                currency,
                BigDecimal.valueOf(mayor).add(BigDecimal.valueOf(minor, scale))
        );
    }

    public static Money of(Currency currency, BigDecimal amount) {
        int scale = currency.getDefaultFractionDigits();
        return new Money(
                currency,
                amount
        );
    }

    public Money add(Money augend) {
        if (!this.currency.equals(augend.currency())) {
            throw new IllegalArgumentException("Currency %s of augend does not match this money's currency %s"
                    .formatted(augend.currency(), this.currency));
        }

        return new Money(this.currency, this.amount.add(augend.amount));
    }

    public Money multiply(int multiplicand) {
        return new Money(this.currency, this.amount.multiply(BigDecimal.valueOf(multiplicand)));
    }
}
