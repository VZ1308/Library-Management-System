package Class;

public class Employee extends Person {
    private int employeeId;
    private int workingHours;
    private double salary;
    private String position;

    protected Employee(String name, String forename, int number, String email, String address) {
        super(name, forename, number, email, address);
    }
    @Override
    public void anzeigen() {
        System.out.println("Employee-Details: ");
        System.out.println("EmployeeId: " + employeeId);
        super.anzeigen();
        System.out.println("Working hours: " + workingHours);
        System.out.println("Salary: " + salary);
        System.out.println("Position: " + position);

    }
    @Override
    public String getTableName() {
        return "Employee";
    }

    @Override
    public String getInsertSQL() {
        return String.format(
                "INSERT INTO employee (name, forename, number, email, address, workingHours, salary, position ) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                getName(), getForename(), getNumber(), getEmail(), getAddress(), getWorkingHours(), getSalary(), getPosition()
        ); //String.format() ermöglicht es uns, Zeichenfolgen mit Platzhaltern zu erstellen, die später mit Werten gefüllt werden
    }
// SETTER
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    public int getWorkingHours() {
        return workingHours;
    }
    public Double getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }

}
