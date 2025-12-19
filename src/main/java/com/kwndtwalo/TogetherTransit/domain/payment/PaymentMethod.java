package com.kwndtwalo.TogetherTransit.domain.payment;
/*Purpose: This class is used for selecting which payment method will be used for paying the driver.
* It does not store the card details although at first it requires them.
* However, it stores the getaway token and provider and last 4 digits of the card.*/

import com.kwndtwalo.TogetherTransit.domain.users.Parent;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentMethodId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Parent parent;

    @Enumerated(EnumType.STRING)
    private MethodType methodType;

    private String provider;      // Stripe, PayFast, PayPal
    private String token;         // Encrypted / gateway token

    private String brand;         // Visa, MasterCard
    private String lastFourDigits;// this is used to indicate the last 4 digits of the user card. For example "Visa ****1234". This helps user to know which card they are using.

    private boolean is_active;       // Allows disabling a card

    private LocalDateTime createdAt;

    public enum MethodType {
        CARD,
        GOOGLE_PAY,
        APPLE_PAY,
        PAYPAL,
        EFT
    }

    protected PaymentMethod() {}

    private PaymentMethod(Builder builder) {
        paymentMethodId = builder.paymentMethodId;
        parent = builder.parent;
        methodType = builder.methodType;
        provider = builder.provider;
        token = builder.token;
        brand = builder.brand;
        lastFourDigits = builder.lastFourDigits;
        is_active = builder.is_active;
        createdAt = builder.createdAt;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public Parent getParent() {
        return parent;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public String getProvider() {
        return provider;
    }

    public String getToken() {
        return token;
    }

    public String getBrand() {
        return brand;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "paymentMethodId=" + getPaymentMethodId() +
                ", methodType=" + getMethodType() +
                ", provider='" + getProvider() + '\'' +
                ", token='" + getToken() + '\'' +
                ", brand='" + getBrand() + '\'' +
                ", lastFourDigits='" + getLastFourDigits() + '\'' +
                ", is_active=" + getIs_active() +
                ", createdAt=" + getCreatedAt() +
                ", parent=" + getParent() +
                '}';
    }

    public static class Builder {
        private Long paymentMethodId;
        private Parent parent;
        private MethodType methodType;
        private String provider;
        private String token;
        private String brand;
        private String lastFourDigits;
        private boolean is_active;
        private LocalDateTime createdAt;

        public Builder setPaymentMethodId(Long paymentMethodId) {
            this.paymentMethodId = paymentMethodId;
            return this;
        }
        public Builder setParent(Parent parent) {
            this.parent = parent;
            return this;
        }
        public Builder setMethodType(MethodType methodType) {
            this.methodType = methodType;
            return this;
        }
        public Builder setProvider(String provider) {
            this.provider = provider;
            return this;
        }
        public Builder setToken(String token) {
            this.token = token;
            return this;
        }
        public Builder setBrand(String brand) {
            this.brand = brand;
            return this;
        }
        public Builder setLastFourDigits(String lastFourDigits) {
            this.lastFourDigits = lastFourDigits;
            return this;
        }
        public Builder setIs_active(boolean is_active) {
            this.is_active = is_active;
            return this;
        }
        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder copy(PaymentMethod paymentMethod) {
            this.paymentMethodId = paymentMethod.getPaymentMethodId();
            this.parent = paymentMethod.getParent();
            this.methodType = paymentMethod.getMethodType();
            this.provider = paymentMethod.getProvider();
            this.token = paymentMethod.getToken();
            this.brand = paymentMethod.getBrand();
            this.lastFourDigits = paymentMethod.getLastFourDigits();
            this.is_active = paymentMethod.getIs_active();
            this.createdAt = paymentMethod.getCreatedAt();
            return this;
        }

        public PaymentMethod build() {return new PaymentMethod(this);}
    }
}
