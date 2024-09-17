package Class;

public class User extends Person {
    private final int userId;

    protected User(String name, String forename, int number, String email, String address, int userId) {
        super(name, forename, number, email, address);
        this.userId = userId;
    }

    @Override
    public void anzeigen() {
        System.out.println("User-Details:");
        System.out.println("ID: " + userId);
        super.anzeigen(); // Ruft die Implementierung der Methode aus der Basisklasse auf
    }
    @Override
    public String getTableName() {
        return "User";
    }

    @Override
    public String getInsertSQL() {
        return String.format(
                "INSERT INTO users (name, forename, number, email, address) VALUES ('%s', '%s', '%s', '%s', '%s')",
                getName(), getForename(), getNumber(), getEmail(), getAddress()
        ); //String.format() ermöglicht es uns, Zeichenfolgen mit Platzhaltern zu erstellen, die später mit Werten gefüllt werden
    }
}
