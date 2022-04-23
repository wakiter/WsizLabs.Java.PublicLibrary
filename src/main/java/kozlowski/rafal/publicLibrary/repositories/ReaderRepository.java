package kozlowski.rafal.publicLibrary.repositories;

import kozlowski.rafal.publicLibrary.model.Reader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ReaderRepository extends CrudRepository<Reader, Long> {
}
