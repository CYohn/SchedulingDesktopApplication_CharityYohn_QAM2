package Objects;


public class FirstLevelDivision {
    private int divisionId;
    private String division;
    private int countryId;

    /**
     * Constructor used upon retrieval of division information from the database, then used to populate combo boxes
     *
     * @param divisionId The division ID
     * @param division   The division name
     * @param countryId  The associated country ID
     */
    public FirstLevelDivision(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

}
