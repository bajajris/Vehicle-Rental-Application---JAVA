package rishabh_bajaj_project;

/**
 * FourWheel class that depicts a FourWheel vehicle and need to be extended by
 * subclasses
 *
 * @author rishabhbajaj
 */
public abstract class FourWheel {

    // datatfields of FourWheel class
    private String make;
    private String year;
    private String baseRent;

    /**
     * constructor to initialize the object and add to arrayList
     *
     * @param make make of Car
     * @param year year of make
     * @param baseRent base rent decided
     */
    public FourWheel(String make, String year, String baseRent) {
        StringBuilder yearBuilder = new StringBuilder(year);

        this.make = make;

        if (Integer.parseInt(baseRent) < 0) {
            throw new IllegalArgumentException("Rent cannot be less than 0");
        } else {
            this.baseRent = baseRent;
        }

        yearBuilder.deleteCharAt(1); // removing the , from the year string - added by TextFormatter
        year = yearBuilder.toString();
        if (Integer.parseInt(year) < 1900 || Integer.parseInt(year) > 2019) {
            throw new IllegalArgumentException("Year cannot be less than 1900 and greater than 2019!!");
        } else {
            this.year = year;
        }
    }

    /**
     * read file line to add to arrayList
     *
     * @param linein
     */
    public FourWheel(String linein) {
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setYear(String year) {
        if (Integer.parseInt(year) < 1900 || Integer.parseInt(year) > 2019) {
            throw new IllegalArgumentException("Year cannot be less than 1900 and greater than 2019!!");
        } else {
            this.year = year;
        }
    }

    public void setBaseRent(String baseRent) {
        if (Integer.parseInt(baseRent) < 0) {
            throw new IllegalArgumentException("Rent cannot be less than 0");
        } else {
            this.baseRent = baseRent;
        }
    }

    public String getMake() {
        return make;
    }

    public String getYear() {
        return year;
    }

    public String getBaseRent() {
        return baseRent;
    }

}
