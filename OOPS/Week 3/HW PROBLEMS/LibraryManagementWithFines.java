import java.util.*;

class BookL {
    String bookId, title, author, isbn, category;
    boolean isIssued;
    String issueDate, dueDate;
    static int totalBooks = 0;
    static String libraryName = "Unset Library";
    static double finePerDay = 2.0;
    static int maxBooksAllowed = 3;
    private static int bid = 0;
    int timesIssued = 0;

    public BookL(String title, String author, String isbn, String category) {
        this.bookId = "BK" + String.format("%04d", ++bid);
        this.title = title; this.author = author; this.isbn = isbn; this.category = category;
        this.isIssued = false; this.issueDate = ""; this.dueDate = "";
        totalBooks++;
    }

    void issueBook(String issueDate, String dueDate) {
        if (isIssued) { System.out.println(title + " already issued."); return; }
        this.isIssued = true; this.issueDate = issueDate; this.dueDate = dueDate; timesIssued++;
    }

    void returnBook(String returnDate) {
        if (!isIssued) { System.out.println(title + " was not issued."); return; }
        this.isIssued = false; this.issueDate=""; this.dueDate="";
    }

    double calculateFine(int overdueDays) {
        return overdueDays > 0 ? overdueDays * finePerDay : 0;
    }

    void renewBook(String newDue) {
        if (!isIssued) { System.out.println("Cannot renew; not issued."); return; }
        this.dueDate = newDue;
    }

    boolean searchMatch(String query) {
        query = query.toLowerCase();
        return title.toLowerCase().contains(query) || author.toLowerCase().contains(query) || isbn.toLowerCase().contains(query);
    }

    void reserveBook() { System.out.println(title + " reserved."); }
}

class MemberL {
    String memberId, memberName, memberType, membershipDate;
    BookL[] booksIssued;
    double totalFines;
    static int totalMembers = 0;
    private static int mid = 0;

    public MemberL(String name, String type, String date) {
        this.memberId = "MB" + String.format("%04d", ++mid);
        this.memberName = name; this.memberType = type; this.membershipDate = date;
        this.booksIssued = new BookL[BookL.maxBooksAllowed + ("Faculty".equals(type)?2:0)];
        this.totalFines = 0;
        totalMembers++;
    }

    boolean canBorrow() { 
        int count=0; for (BookL b: booksIssued) if (b!=null) count++;
        return count < booksIssued.length;
    }

    void issueBook(BookL book, String issueDate, String dueDate) {
        if (!canBorrow()) { System.out.println(memberName + " reached borrowing limit."); return; }
        if (book.isIssued) { System.out.println("Book unavailable."); return; }
        int i=0; while (i<booksIssued.length && booksIssued[i]!=null) i++;
        if (i<booksIssued.length) { booksIssued[i]=book; book.issueBook(issueDate, dueDate); }
    }

    void returnBook(BookL book, int overdueDays) {
        for (int i=0; i<booksIssued.length; i++) {
            if (booksIssued[i]==book) {
                double fine = book.calculateFine(overdueDays);
                totalFines += fine;
                booksIssued[i]=null;
                book.returnBook("today");
                System.out.println(memberName + " returned " + book.title + " | Fine: ₹" + fine);
                return;
            }
        }
        System.out.println("This member does not hold the book.");
    }
}

public class LibraryManagementWithFines {
    static void generateLibraryReport(BookL[] books, MemberL[] members) {
        int issued=0; for (BookL b: books) if (b!=null && b.isIssued) issued++;
        System.out.println("=== " + BookL.libraryName + " Report ===");
        System.out.println("Books: " + BookL.totalBooks + " | Members: " + MemberL.totalMembers);
        System.out.println("Issued Now: " + issued);
        // Most popular by timesIssued
        BookL popular=null; int max=-1;
        for (BookL b: books) if (b!=null && b.timesIssued>max) {max=b.timesIssued; popular=b;}
        System.out.println("Most Popular: " + (popular==null?"N/A":popular.title));
    }

    static BookL[] getOverdueBooks(BookL[] books) {
        // Without real dates, return those currently issued as a placeholder
        int c=0; for (BookL b: books) if (b!=null && b.isIssued) c++;
        BookL[] out = new BookL[c]; int i=0;
        for (BookL b: books) if (b!=null && b.isIssued) out[i++]=b;
        return out;
    }

    static BookL[] getMostPopularBooks(BookL[] books, int n) {
        BookL[] copy = Arrays.copyOf(books, books.length);
        Arrays.sort(copy, new Comparator<BookL>(){
            public int compare(BookL a, BookL b){
                if (a==null && b==null) return 0;
                if (a==null) return 1;
                if (b==null) return -1;
                return Integer.compare(b.timesIssued, a.timesIssued);
            }
        });
        int m=Math.min(n, copy.length);
        BookL[] res = new BookL[m];
        for (int i=0;i<m;i++) res[i]=copy[i];
        return res;
    }

    public static void main(String[] args) {
        BookL.libraryName = "City Central Library";
        BookL.finePerDay = 5.0;
        BookL.maxBooksAllowed = 2;

        BookL[] books = {
            new BookL("Java Basics","Sharma","ISBN001","Tech"),
            new BookL("Data Structures","Roy","ISBN002","Tech"),
            new BookL("Algorithms","Das","ISBN003","Tech"),
            new BookL("Poems","Tagore","ISBN004","Lit"),
            new BookL("History of India","Sen","ISBN005","History")
        };

        MemberL s = new MemberL("Neha","Student","2025-01-10");
        MemberL f = new MemberL("Dr. Rao","Faculty","2024-08-01");
        MemberL g = new MemberL("Arun","General","2025-03-15");
        MemberL[] members = {s,f,g};

        s.issueBook(books[0], "2025-09-01", "2025-09-10");
        f.issueBook(books[1], "2025-09-01", "2025-09-15");
        g.issueBook(books[3], "2025-09-02", "2025-09-09");

        s.returnBook(books[0], 3); // overdue 3 days
        f.renewBook(books[1].dueDate = "2025-09-20"); // extend due date
        g.returnBook(books[3], 0);

        generateLibraryReport(books, members);
        System.out.println("Overdue (simulated): " + getOverdueBooks(books).length);
        BookL[] popular = getMostPopularBooks(books, 3);
        System.out.println("Top popular: " + (popular[0]!=null?popular[0].title:"N/A"));
    }
}
