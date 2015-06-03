package sbk.sprtest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sbk.sprtest.domain.PersonalPost;

public interface PersonalPostRepository extends JpaRepository<PersonalPost, Long> {
}
