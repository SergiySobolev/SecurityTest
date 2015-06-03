package sbk.sprtest.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbk.sprtest.domain.AdminPost;
import sbk.sprtest.domain.Post;
import sbk.sprtest.repo.AdminPostRepository;

import static com.google.common.collect.FluentIterable.from;

@Service("adminService")
public class AdminService implements  GenericService {

	@Autowired
	private AdminPostRepository adminPostRepository;

	private Map<Long, AdminPost> adminPosts = new HashMap<Long, AdminPost>();
	
	public AdminService() {
		init();
	}

	@Override
	public Post getSingle(Long id) {
		return null;
	}


	public List<AdminPost> getAll() {
		return adminPostRepository.findAll();
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

	private void init() {
		// Create new post
		AdminPost post1 = new AdminPost();
		post1.setId(1L);
		post1.setCreatedDate(new Date());
		post1.setMessage("This is admin's post #1");
		
		// Create new post
		AdminPost post2 = new AdminPost();
		post2.setId(2L);
		post2.setCreatedDate(new Date());
		post2.setMessage("This is admin's post #2");
		
		// Create new post
		AdminPost post3 = new AdminPost();
		post3.setId(3L);
		post3.setCreatedDate(new Date());
		post3.setMessage("This is admin's post #3");
		
		// Add to adminPosts
		adminPosts.put(post1.getId(), post1);
		adminPosts.put(post2.getId(), post2);
		adminPosts.put(post3.getId(), post3);
	}
}
