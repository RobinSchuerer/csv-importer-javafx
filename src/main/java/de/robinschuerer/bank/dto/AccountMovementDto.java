package de.robinschuerer.bank.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import de.robinschuerer.bank.util.ConverterUtil;

public class AccountMovementDto {

    private String movementDate;
    private String description;
    private BigDecimal value;
    private String tagProposal;
    private String tag;
    private boolean disabled;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(final boolean disabled) {
        this.disabled = disabled;
    }

    public String getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(final String movementDate) {
        this.movementDate = movementDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getValue() {
        return ConverterUtil.asCurrency(value);
    }

    public double getValueAsNumber(){
        return value.doubleValue();
    }

    public void setValue(final String value) {
        this.value = new BigDecimal(value);
    }

    public String getTagProposal() {
        return tagProposal;
    }

    public void setTagProposal(final String tagProposal) {
        this.tagProposal = tagProposal;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public LocalDate getBuchungsDatum(){
        return LocalDate.parse(getMovementDate(), DateTimeFormatter
            .ofPattern("dd.MM.yyyy"));
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static final class Builder {

        private String movementDate;
        private String description;
        private BigDecimal value;
        private String tagProposal;
        private String tag;

        private Builder() {
        }

        public Builder withMovementDate(String buchung) {
            this.movementDate = buchung;
            return this;
        }

        public Builder withDescription(String auftraggeberEmpfaenger) {
            this.description = auftraggeberEmpfaenger;
            return this;
        }

        public Builder withValue(final BigDecimal betrag) {
            this.value = betrag;
            return this;
        }

        public Builder withTagProposal(String proposal) {
            this.tagProposal = proposal;
            return this;
        }

        public Builder withTag(String tag) {
            this.tag = tag;
            return this;
        }

        public AccountMovementDto build() {
            final AccountMovementDto accountMovementDto = new AccountMovementDto();
            accountMovementDto.setMovementDate(movementDate);
            accountMovementDto.setDescription(description);
            accountMovementDto.value = this.value;
            accountMovementDto.setTagProposal(tagProposal);
            accountMovementDto.setTag(tag);
            return accountMovementDto;
        }
    }

    @Override
    public String toString() {
        return "BuchungSatz{" +
            "movementDate='" + movementDate + '\'' +
            ", description='" + description + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
