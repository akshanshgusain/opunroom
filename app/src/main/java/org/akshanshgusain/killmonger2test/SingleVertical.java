package org.akshanshgusain.killmonger2test;

public class SingleVertical {
    private String companyName, coompanyDP, CompanyBanner;

    public SingleVertical(String companyName, String coompanyDP, String companyBanner) {
        this.companyName = companyName;
        this.coompanyDP = coompanyDP;
        CompanyBanner = companyBanner;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCoompanyDP() {
        return coompanyDP;
    }

    public void setCoompanyDP(String coompanyDP) {
        this.coompanyDP = coompanyDP;
    }

    public String getCompanyBanner() {
        return CompanyBanner;
    }

    public void setCompanyBanner(String companyBanner) {
        CompanyBanner = companyBanner;
    }
}
