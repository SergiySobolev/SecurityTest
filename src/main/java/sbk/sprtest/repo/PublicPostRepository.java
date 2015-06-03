package sbk.sprtest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sbk.sprtest.domain.PublicPost;

public interface PublicPostRepository extends JpaRepository<PublicPost, Long> {
}
