package com.dbc.assembleia.datasource.invertexto;

import java.io.Serializable;

public class CpfValid implements Serializable {

    private boolean valid;
    private String formatted;

    public CpfValid() {
    }

    public CpfValid(boolean valid, String formatted) {
        this.valid = valid;
        this.formatted = formatted;
    }

    public CpfValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }
}
