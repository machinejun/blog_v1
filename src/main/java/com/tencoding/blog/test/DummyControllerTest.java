package com.tencoding.blog.test;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.model.Board;
import com.tencoding.blog.model.RoleType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;
import com.tencoding.blog.service.BoardService;

@RestController
public class DummyControllerTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/dummy/join")
	public String join(@RequestBody User user) {
		System.out.println("------------------------------");
		System.out.println(">>>  " + user.getUsername());
		System.out.println(">>>  " + user.getPassword());
		System.out.println(">>>  " + user.getEmail());
		System.out.println("------------------------------");
		
		System.out.println("------------------------------");
		System.out.println(">>>  " + user.getId());
		System.out.println(">>>  " + user.getCreateDate());
		System.out.println(">>>  " + user.getRole());
		System.out.println("------------------------------");
		
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
		return "회원가입 완료 ";
		
		
	}
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// Optional : null safety  ( Optional 로 감싸서 user Entity를 가지고 오겠다 )
		// 
//		User tempUser1 = userRepository.findById(id).get(); // null 이 될수도 있다.  //  ->> 검색시 없을 경우 noSuchElementException
//		
//		// Optional
//		User tempUser2 = userRepository.findById(id).orElseGet(()->{        //->> {0, null, null ... }
//			return new User();
//		});
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없는 사용자 입니다.");  //  ->> IllegalAr... + message
		});
		
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	// 페이징 처리
	// http://localhost:9090/blog/dummy/user?page=0
	@GetMapping("/dummy/user")
	public Page<Board> pageList(@PageableDefault(size = 4, sort = "id"
	, direction = Direction.DESC) Pageable pageable){
		// 원하는대로 리턴 하면 된다. list or page
		return boardService.getBoardList(pageable);
	}
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User reqUser) {
		System.out.println("id : " + id + "<<< reqUser : " + reqUser.getPassword());
		System.out.println("id : " + id + "<<< reqUser : " + reqUser.getEmail());
		// save 할 때 userName 값이 없기 때문에 select 해와서 추가 적인 처리를 해야한다.
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없는 사용자 입니다.");
		});
		userEntity.setPassword(reqUser.getPassword());
		userEntity.setEmail(reqUser.getEmail());
		//User user = userRepository.save(userEntity); // 없으면 insert, 있으면 update 처리한다.
		// save 를 해도 업데이트가 있지만 더티체킹을 고려해서 Transaction 을 사용하는것이 훨씬 좋다
		
		
		return null;
	}
	
	@Transactional
	@PutMapping("/dummy/user2/{id}")
	public User updateUser2(@PathVariable int id, @RequestBody User newUser) {
		User tempUser = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없는 사용자 입니다.");
		});
		tempUser.setUsername(newUser.getUsername());
		tempUser.setPassword(newUser.getPassword());
		tempUser.setEmail(newUser.getEmail());
		tempUser.setRole(RoleType.ADMIN);
		
		return tempUser;
	}
	
	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch (Exception e) {
			return "해당 사용자는 없습니다";
		}
		
		return id + "는 삭제 되었습니다";
	}
	
	

}
