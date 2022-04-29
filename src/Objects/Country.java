package Objects;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Country {

    private int countryId;
    private String country;
//    private LocalDateTime createDate;
//    private LocalDateTime createdBy;
//    private Timestamp lastUpdate;
//    private String lastUpdatedBy;

    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
//        this.createDate = createDate;
//        this.createdBy = createdBy;
//        this.lastUpdate = lastUpdate;
//        this.lastUpdatedBy = lastUpdatedBy;
    }


    public Country(String country) {
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

//    public LocalDateTime getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(LocalDateTime createDate) {
//        this.createDate = createDate;
//    }
//
//    public LocalDateTime getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(LocalDateTime createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public Timestamp getLastUpdate() {
//        return lastUpdate;
//    }
//
//    public void setLastUpdate(Timestamp lastUpdate) {
//        this.lastUpdate = lastUpdate;
//    }
//
//    public String getLastUpdatedBy() {
//        return lastUpdatedBy;
//    }
//
//    public void setLastUpdatedBy(String lastUpdatedBy) {
//        this.lastUpdatedBy = lastUpdatedBy;
//    }
}
