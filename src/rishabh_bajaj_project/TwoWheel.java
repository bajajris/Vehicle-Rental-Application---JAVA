package rishabh_bajaj_project;

/**
 * TwoWheel class that depicts a Two wheel vehicle and need to be extended by
 * subclasses for implementation
 *
 * @author rishabhbajaj
 */
public abstract class TwoWheel {

    // datatfields of TwoWheel class
    private String make;
    private String model;
    private String year;
    private String baseRent;

    /**
     *
     * @param make make of the vehicle
     * @param model model number/type
     * @param year year of make
     * @param baseRent base rent decided for renting
     */
    public TwoWheel(String make, String model, String year, String baseRent) {
        StringBuilder yearBuilder = new StringBuilder(year);

        this.make = make;
        this.model = model;

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
     *
     * @param linein accepts a single line to break into parts to read a
     * comma-delimited file
     */
    public TwoWheel(String linein) {
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getBaseRent() {
        return baseRent;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
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
}
