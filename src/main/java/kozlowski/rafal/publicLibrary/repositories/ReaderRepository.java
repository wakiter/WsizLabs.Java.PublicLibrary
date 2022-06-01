package kozlowski.rafal.publicLibrary.repositories;

import kozlowski.rafal.publicLibrary.model.Reader;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReaderRepository extends CrudRepository<Reader, Long> {
    Iterable<Reader> findByLastnameContainsIgnoreCase(@Param("lastname") String lastname);

    Iterable<Reader> findByBooksBookNameContainsIgnoreCase(@Param("name") String bookname);

    Iterable<Reader> findByBooksBookUniversalIdentificationNumberContainsIgnoreCase(@Param("universalIdentificationNumber") String universalIdentificationNumber);

    @Query(value = "select r.* from reader r\n" +
            "where r.id in (\n" +
            "select bb.reader_id from borrowed_book bb where bb.reader_id = r.id group by bb.reader_id having count(bb.reader_id) = :count\n" +
            ")", nativeQuery = true)
    Iterable<Reader> findByCountOfNotReturnedBooks(@Param("count") Long count);
}
