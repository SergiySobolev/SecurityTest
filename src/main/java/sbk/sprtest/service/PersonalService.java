package sbk.sprtest.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import sbk.sprtest.domain.PersonalPost;
import sbk.sprtest.domain.Post;
import sbk.sprtest.repo.PersonalPostRepository;

import static com.google.common.collect.Lists.newArrayList;


@Service("personalService")
public class PersonalService implements GenericService {

	@Autowired
	public PersonalPostRepository personalPostRepository;
	
	public PersonalService() {

	}

	@Override
	public Post getSingle(Long id) {
		return null;
	}

	@PostFilter("hasPermission(filterObject, 'READ') or hasPermission(filterObject, 'WRITE')")
	public List<PersonalPost> getAll() {
		return personalPostRepository.findAll();
	}
	
	public Boolean add(Post post)  {
		return true;
	}
	
	@PreAuthorize("hasPermission(#post, 'WRITE')")
	public Boolean edit(Post post)  {
		return true;
	}

	@PreAuthorize("hasPermission(#post, 'WRITE')")
	public Boolean delete(Post post)  {
		return true;
	}

}
