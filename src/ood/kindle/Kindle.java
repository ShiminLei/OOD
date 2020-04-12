package ood.kindle;

import java.util.ArrayList;
import java.util.List;

public class Kindle {
    private List<Book> books;
    private EBookReaderFactory readerFactory;

    public Kindle() {
        books = new ArrayList<>();
        readerFactory = new EBookReaderFactory();
    }

    public String readBook(Book book) throws Exception {
        EBookReader reader = readerFactory.createReader(book);
        if (reader == null) {
            throw new Exception("Can't read this format");
        }
        return reader.displayReaderType() + ", book content is: " + reader.readBook();
    }

    public void downloadBook(Book b) {
        books.add(b);
    }

    public void uploadBook(Book b) {
        books.add(b);
    }

    public void deleteBook(Book b) {
        books.remove(b);
    }
}

