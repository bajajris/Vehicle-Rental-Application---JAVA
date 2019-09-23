package rishabh_bajaj_project;

/**
 * Bike class models a bike that extends TwoWheel and implements Vehicle
 *
 * @author rishabhbajaj
 */
public class Bike extends TwoWheel implements Vehicle {

    //datafields of Bike class
    private String body;
    private String engine;

    /**
     *
     * @param linein accepts a single line to break into parts to read a
     * comma-delimited file
     */
    public Bike(String linein) {
        super(linein);
        String[] parts = linein.split(":");
        super.setMake(parts[0].trim());
        super.setYear(parts[1].trim());
        super.setBaseRent(parts[5].trim());
        this.body = parts[3].trim();
        this.engine = parts[4].trim();
        super.setModel(parts[2].trim());
    }

    /**
     *
     * @param body bike body Type
     * @param engine engine type
     * @param make make of bike
     * @param model model number/type
     * @param year year of make
     * @param baseRent base rent decided
     */
    public Bike(String body, String engine, String make, String model, String year, String baseRent) {
        super(make, model, year, baseRent);
        this.body = body;
        this.engine = engine;
    }

    public String getBody() {
        return body;
    }

    public String getEngine() {
        return engine;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    /**
     * Add data to a file Overriding abstract method in super class
     *
     * @return formatted string to add to a file
     */
    @Override
    public String toString() {
        String format = "%s : %s : %s : %s : %s : %s";
        return String.format(format, getMake(), getYear(), getModel(), getBody(), getEngine(), getBaseRent());
    }

}
