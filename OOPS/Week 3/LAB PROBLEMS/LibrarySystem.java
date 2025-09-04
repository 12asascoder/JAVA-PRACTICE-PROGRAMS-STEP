class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isAvailable;
    private static int totalBooks = 0;
    private static int availableBooks = 0;
    private static int counter = 0;

    public Book(String title, String author) {
        this.bookId = generateBookId();
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        totalBooks++;
        availableBooks++;
    }

    private static String generateBookId() {
        counter++;
        return "B" + String.format("%03d", counter);
    }

    public void issueBook() {
        if (isAvailable) {
            isAvailable = false;
            availableBooks--;
        } else {
            System.out.println(title + " is already issued.");
        }
    }

    public void returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            availableBooks++;
        }
    }

    public void displayBookInfo() {
        System.out.println("Book ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Available: " + isAvailable);
    }

    public boolean isAvailable() { return isAvailable; }
    public String getBookId() { return bookId; }

    public static int getAvailableBooks() { return availableBooks; }
    public static int getTotalBooks() { return totalBooks; }
}

class Member {
    private String memberId;
    private String memberName;
    private String[] booksIssued;
    private int bookCount;
    private static int counter = 0;

    public Member(String memberName) {
        this.memberId = generateMemberId();
        this.memberName = memberName;
        this.booksIssued = new String[5];
        this.bookCount = 0;
    }

    private static String generateMemberId() {
        counter++;
        return "M" + String.format("%03d", counter);
    }

    public void borrowBook(Book book) {
        if (book.isAvailable() && bookCount < booksIssued.length) {
            book.issueBook();
            booksIssued[bookCount++] = book.getBookId();
            System.out.println(memberName + " borrowed " + book.getBookId());
        } else {
            System.out.println("Book not available or limit reached.");
        }
    }

    public void returnBook(String bookId, Book[] books) {
        for (int i = 0; i < bookCount; i++) {
            if (booksIssued[i].equals(bookId)) {
                for (Book b : books) {
                    if (b.getBookId().equals(bookId)) {
                        b.returnBook();
                        System.out.println(memberName + " returned " + bookId);
                        booksIssued[i] = null;
                        break;
                    }
                }
            }
        }
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Book[] books = {
            new Book("Java Programming", "Author A"),
            new Book("Python Basics", "Author B"),
            new Book("C++ Guide", "Author C")
        };

        Member m1 = new Member("Alice");
        Member m2 = new Member("Bob");

        m1.borrowBook(books[0]);
        m2.borrowBook(books[1]);
        m1.returnBook("B001", books);

        for (Book b : books) {
            b.displayBookInfo();
        }

        System.out.println("Total Books: " + Book.getTotalBooks() + ", Available Books: " + Book.getAvailableBooks());
    }
}