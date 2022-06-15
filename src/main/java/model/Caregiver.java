package model;

public class Caregiver extends Person {
    private long cid;
    private String phone;

    /**
     * constructs a caregiver from the given params.
     *
     * @param firstName
     * @param surname
     * @param phone
     */
    public Caregiver(String firstName, String surname, String phone) {
        super(firstName, surname);
        this.phone = phone;
        this.cid = 0;
    }

    /**
     * constructs a caregiver from the given params.
     *
     * @param firstName
     * @param surname
     * @param cid
     * @param phone
     */
    public Caregiver(long cid, String firstName, String surname, String phone) {
        super(firstName, surname);
        this.cid = cid;
        this.phone = phone;
    }

    public long getCid() {
        return cid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return string-representation of the caregiver
     */
    public String toString() {
        return "Caregiver" + "\nID: " + this.cid +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nPhone: " + this.phone +
                "\n";
    }
}
