package com.spring.example.d1;

/**
 * Created by adnenehamdouni on 20/07/2018.
 */

public enum ProfessionStatus {
    UNSPECIFIED("UNSPECIFIED"),
    WITHOUT_PROFESSION("Without tariff"),
    WITH_PROFESSION("With tariff");

    private String status;

    ProfessionStatus(String professionStatus) {
        this.status = professionStatus;
    }

    public String getProfessionStatus() {
        return status;
    }

}
