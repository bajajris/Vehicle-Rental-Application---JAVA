package rishabh_bajaj_project;

/**
 * Car class models a car that extends FourWheel and implements Vehicle
 *
 * @author rishabhbajaj
 */
public class Car extends FourWheel implements Vehicle {

    // datatfields of Car class
    private String body;
    private String noOfSeats;
    private String transmission;

    /**
     *
     * @param linein accepts a single line to break into parts to read a
     * comma-delimited file
     */
    public Car(String linein) {
        super(linein); // calling the constructor of FourWheel class
        String[] parts = linein.split(":");
        super.setMake(parts[0].trim());
        super.setYear(parts[1].trim());
        super.setBaseRent(parts[3].trim());
        this.body = parts[4].trim();
        this.transmission = parts[5].trim();
        this.noOfSeats = parts[2].trim();
    }

    /**
     *
     * @param body car bodyType
     * @param noOfSeats number of seats
     * @param transmission transmission type
     * @param make make of car
     * @param year year of make
     * @param baseRent baseRent decided for the car
     */
    public Car(String body, String noOfSeats, String transmission, String make, String year, String baseRent) {
        super(make, year, baseRent);
        this.body = body;
        this.noOfSeats = noOfSeats;
        this.transmission = transmission;
    }

    public String getBody() {
        return body;
    }

    public String getNoOfSeats() {
        return noOfSeats;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setNoOfSeats(String noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    /**
     * Add data to a file Overriding abstract method in super class
     *
     * @return formatted string to add to a file
     */
    @Override
    public String toString() {
        String format = "%s : %s : %s : %s : %s : %s";
        return String.format(format, getMake(), getYear(), getNoOfSeats(), getBaseRent(), getBody(), getTransmission());
    }
}
