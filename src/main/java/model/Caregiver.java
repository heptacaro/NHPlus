package model;

public class Caregiver extends Person {
    private long cid;
    private String phone;
    private String loginUsername;
    private String loginPassword;

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
     * @param loginUsername
     * @param loginPassword
     */
    public Caregiver(long cid, String firstName, String surname, String phone, String loginUsername, String loginPassword) {
        super(firstName, surname);
        this.cid = cid;
        this.phone = phone;
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
    }

    public long getCid() {
        return cid;
    }

    public String getPhone() {
        return phone;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
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
