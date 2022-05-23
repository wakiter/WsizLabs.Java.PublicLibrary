package kozlowski.rafal.publicLibrary.model;

import org.hibernate.annotations.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
public final class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String universalIdentificationNumber;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BorrowedBook> borrows = new HashSet<BorrowedBook>();

    public Book(String name, String universalIdentificationNumber) {
        this.name = name;
        this.universalIdentificationNumber = universalIdentificationNumber;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversalIdentificationNumber() {
        return universalIdentificationNumber;
    }

    public void setUniversalIdentificationNumber(String universalIdentificationNumber) {
        this.universalIdentificationNumber = universalIdentificationNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<BorrowedBook> getBorrows() {
        return borrows;
    }

    public void setBorrows(Set<BorrowedBook> borrows) {
        this.borrows = borrows;
    }

    public Book borrow(Reader reader, Date borrowDate) {
        this.borrows.add(new BorrowedBook(reader, this, borrowDate, null));

        return this;
    }
}
