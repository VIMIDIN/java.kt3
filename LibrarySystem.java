import java.util.ArrayList;
import java.util.List;

// Перечисление жанров книг
enum Genre {
    FICTION, NON_FICTION
}

// Абстрактный класс Book
abstract class Book implements Readable {
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    @Override
    public void read() {
        System.out.println("Reading " + title + " by " + author);
    }
}

// Класс FictionBook
class FictionBook extends Book {
    public FictionBook(String title, String author, int year) {
        super(title, author, year);
    }

    @Override
    public void read() {
        System.out.println("Reading a fiction book: " + getTitle());
    }
}

// Класс NonFictionBook
class NonFictionBook extends Book {
    public NonFictionBook(String title, String author, int year) {
        super(title, author, year);
    }

    @Override
    public void read() {
        System.out.println("Reading a non-fiction book: " + getTitle());
    }
}

// Интерфейс Readable
interface Readable {
    void read();
}

// Исключение для недоступной книги
class BookUnavailableException extends Exception {
    public BookUnavailableException(String message) {
        super(message);
    }
}

// Класс Library
class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void borrowBook(String title) throws BookUnavailableException {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Borrowing book: " + book.getTitle());
                return;
            }
        }
        throw new BookUnavailableException("Book " + title + " is not available.");
    }

    public void returnBook(String title) {
        System.out.println("Returning book: " + title);
    }

    // Статический вложенный класс LibraryHelper
    static class LibraryHelper {
        public static Book findBookByTitle(List<Book> books, String title) {
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(title)) {
                    return book;
                }
            }
            return null;
        }
    }

    public void findBooksByGenre(Genre genre) {
        books.stream()
                .filter(book -> (genre == Genre.FICTION && book instanceof FictionBook) ||
                        (genre == Genre.NON_FICTION && book instanceof NonFictionBook))
                .forEach(book -> System.out.println("Found book: " + book.getTitle()));
    }
}

// Главный класс для тестирования
public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Создание книг
        Book book1 = new FictionBook("The Great Gatsby", "F. Scott Fitzgerald", 1925);
        Book book2 = new NonFictionBook("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", 2011);
        Book book3 = new FictionBook("1984", "George Orwell", 1949);

        // Добавление книг в библиотеку
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        // Чтение книг
        book1.read();
        book2.read();

        // Поиск книги по названию
        try {
            library.borrowBook("The Great Gatsby");
            library.borrowBook("Unknown Book");
        } catch (BookUnavailableException e) {
            System.out.println(e.getMessage());
        }

        // Поиск книг по жанру
        library.findBooksByGenre(Genre.FICTION);
        library.findBooksByGenre(Genre.NON_FICTION);
    }
}