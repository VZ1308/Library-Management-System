package Class;

public class Books {
    private int buchId;
    private String title;
    private String genre;
    private String autor;
    private String description;
    private boolean borrowed;
    private boolean returnBook;

    public Books(int buchId, String title, String genre, String autor, String description, boolean borrowed, boolean returnBook) {
        this.buchId = buchId;
        this.title = title;
        this.genre = genre;
        this.autor = autor;
        this.description = description;
        this.borrowed = borrowed;
        this.returnBook = returnBook;
    }

    public void anzeigen() {
        System.out.println("Book-Details: ");
        System.out.println("Book ID: " + buchId);
        System.out.println("Title: " + title);
        System.out.println("Genre: " + genre);
        System.out.println("Author: " + autor);
        System.out.println("Description: " + description);
        System.out.println("Borrowed: " + borrowed);
        System.out.println("Return Book: " + returnBook);
    }

    public String getTableName() {
        return "Books";
    }

    public String getInsertSQL() {
        return String.format(
                "INSERT INTO Books (title, genre, autor, description, borrowed, returnBook) VALUES ('%s', '%s', '%s', '%s', %b, %b)",
                title, genre, autor, description, borrowed, returnBook
        );
    }

    // GETTER AND SETTER
    public int getBuchId() {
        return buchId;
    }

    public void setBuchId(int buchId) {
        this.buchId = buchId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public boolean isReturnBook() {
        return returnBook;
    }

    public void setReturnBook(boolean returnBook) {
        this.returnBook = returnBook;
    }
}
