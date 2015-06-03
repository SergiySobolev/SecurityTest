package sbk.sprtest.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbk.sprtest.domain.Post;
import sbk.sprtest.domain.PublicPost;
import sbk.sprtest.repo.PublicPostRepository;

import static com.google.common.collect.Lists.newArrayList;

@Service("publicService")
public class PublicService implements GenericService {

	@Autowired
	private PublicPostRepository publicPostRepository;
	
	public PublicService() {

	}

	@Override
	public Post getSingle(Long id) {
		return null;
	}

	public List<PublicPost> getAll() {
		return publicPostRepository.findAll();
	}
	
	public Boolean add(Post post)  {
		return true;
	}
	
	public Boolean edit(Post post)  {
		return true;
	}

	public Boolean delete(Post post)  {
		return true;
	}

}
