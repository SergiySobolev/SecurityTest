package sbk.sprtest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sbk.sprtest.domain.AdminPost;

public interface AdminPostRepository extends JpaRepository<AdminPost, Long> {
}
