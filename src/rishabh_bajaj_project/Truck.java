package rishabh_bajaj_project;

/**
 * Truck class models a Truck that extends FourWheel and implements Vehicle
 *
 * @author rishabhbajaj
 */
public class Truck extends FourWheel implements Vehicle {

    // datatfields of Truck class
    private String loadingCapacity;

    /**
     *
     * @param loadingCapacity truck loading capacity
     * @param make make of truck
     * @param year year of truck
     * @param baseRent base rent decided for rent
     */
    public Truck(String loadingCapacity, String make, String year, String baseRent) {
        super(make, year, baseRent);
        this.loadingCapacity = loadingCapacity;
    }

    /**
     *
     * @param linein accepts a single line to break into parts to read a comma -
     * delimited file
     */
    public Truck(String linein) {
        super(linein); // calling the constructor of FourWheel class
        String[] parts = linein.split(":");
        super.setMake(parts[0].trim());
        super.setYear(parts[1].trim());
        super.setBaseRent(parts[2].trim());
        this.loadingCapacity = parts[3].trim();
    }

    public String getLoadingCapacity() {
        return loadingCapacity;
    }

    public void setLoadingCapacity(String loadingCapacity) {
        this.loadingCapacity = loadingCapacity;
    }

    /**
     * Add data to a file Overriding abstract method in super class
     *
     * @return formatted string to add to a file
     */
    @Override
    public String toString() {
        String format = "%s : %s : %s : %s";
        return String.format(format, getMake(), getYear(), getBaseRent(), getLoadingCapacity());
    }
}
