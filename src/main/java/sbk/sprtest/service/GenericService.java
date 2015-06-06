package sbk.sprtest.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import sbk.sprtest.domain.Post;

/**
 * A generic service for handling CRUD operations.
 * <p>
 * The method access-control expressions are specified in this interface.
 */

@Service
public interface GenericService {

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public  Post getSingle(Long id);

    public List<? extends Post> getAll();

    @PreAuthorize("hasPermission(#post, 'READ')")
    public Boolean add(Post post);

    @PreAuthorize("hasPermission(#post, 'READ')")
    public Boolean edit(Post post);

    @PreAuthorize("hasPermission(#post, 'READ')")
    public Boolean delete(Post post);

}