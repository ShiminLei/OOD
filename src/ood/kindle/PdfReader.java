package ood.kindle;

class PdfReader extends EBookReader{

    public PdfReader(Book b) {
        super(b);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String readBook() {
        // TODO Auto-generated method stub
        return book.getFormat().getContent();
    }

    @Override
    public String displayReaderType() {
        // TODO Auto-generated method stub
        return "Using PDF reader";
    }
}
