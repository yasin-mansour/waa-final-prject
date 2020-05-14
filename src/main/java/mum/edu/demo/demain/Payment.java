package mum.edu.demo.demain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Payment implements Cloneable {
    public static class PaymentBuilder implements Builder<Payment> {
        private Payment payment;

        public PaymentBuilder() {
            this.payment = new Payment();
        }

        public Payment.PaymentBuilder withId(int id) {
            payment.setId(id);
            return this;
        }

        public Payment.PaymentBuilder withNumber(String number) {
            payment.setCardNumber(number);
            return this;
        }

        public Payment.PaymentBuilder withCode(String code) {
            payment.setCode(code);
            return this;
        }

        public Payment.PaymentBuilder withHolderName(String name) {
            payment.setHolderName(name);
            return this;
        }

        @Override
        public Payment build() {
            return payment;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 16, max = 16)
    private String cardNumber;

    @Size(min = 4, max = 4)
    private String code;

    @Size(min = 4)
    private String holderName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static Payment.PaymentBuilder create() {
        return new Payment.PaymentBuilder();
    }
}
