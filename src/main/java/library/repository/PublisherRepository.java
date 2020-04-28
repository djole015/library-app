package library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import library.model.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long>{

}
