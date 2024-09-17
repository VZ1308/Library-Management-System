package Class;

public abstract class Person {
    private String name;
    private String forename;
    private int number;
    private String email;
    private String address;

    protected Person(String name, String forename, int number, String email, String address) {
        this.name = name;
        this.forename = forename;
        this.number = number;
        this.email = email;
        this.address = address;
    }
// ABSTRACT
    public abstract String getTableName();
    public abstract String getInsertSQL();

// GETTER and SETTER
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // other methods
    public void anzeigen() {
        System.out.println("Name: " + name);
        System.out.println("Forename: " + forename);
        System.out.println("Number: " + number);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
    }


}
