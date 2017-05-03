package de.robinschuerer.buchung.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Login {

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private String email;

        private String password;

        private Builder() {
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Login build() {
            Login login = new Login();
            login.setEmail(email);
            login.setPassword(password);
            return login;
        }
    }
}
