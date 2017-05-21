package de.robinschuerer.bank.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountMovement {

    @JsonProperty("PositionDate")
    private String positionDate;

    @JsonProperty("Tags")
    private List<String> tags;

    @JsonProperty("AccountId")
    private long accountId;

    @JsonProperty("Amount")
    private double amount;

    public String getPositionDate() {
        return positionDate;
    }

    public void setPositionDate(final String positionDate) {
        this.positionDate = positionDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(final List<String> tags) {
        this.tags = tags;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(final long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public static Builder newBuilder() {
        return new Builder();
    }
    public static final class Builder {
        private String positionDate;
        private List<String> tags;
        private long accountId;

        private double amount;

        private Builder() {
        }

        public Builder withPositionDate(String positionDate) {
            this.positionDate = positionDate;
            return this;
        }

        public Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public AccountMovement build() {
            AccountMovement accountMovement = new AccountMovement();
            accountMovement.tags = this.tags;
            accountMovement.positionDate = this.positionDate;
            accountMovement.amount = this.amount;
            accountMovement.accountId = this.accountId;
            return accountMovement;
        }
    }

    @Override
    public String toString() {
        return "AccountMovement{" +
            "positionDate='" + positionDate + '\'' +
            ", amount=" + amount +
            ", tags=" + tags +
            ", accountId=" + accountId +
            '}';
    }
}
