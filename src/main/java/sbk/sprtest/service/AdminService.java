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
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import sbk.sprtest.domain.AdminPost;
import sbk.sprtest.domain.Post;
import sbk.sprtest.repo.AdminPostRepository;

import static com.google.common.collect.FluentIterable.from;

@Service("adminService")
public class AdminService implements  GenericService {

	@Autowired
	private AdminPostRepository adminPostRepository;

	public AdminService() {
	}

	@Override
	public Post getSingle(Long id) {
		return null;
	}

	@PostFilter("hasPermission(filterObject, 'READ') or hasPermission(filterObject, 'WRITE')")
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

}
