package com.ogabe.forum.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogabe.forum.Repository.CollectionRepository;
import com.ogabe.forum.entity.CollectionEntity;
import com.ogabe.user.entity.UserVO;

@Service
public class CollectionService {
	
	@Autowired
	CollectionRepository collection;
	
	public CollectionEntity add(CollectionEntity c) {
		return  collection.save(c);
	}
	
	public List<CollectionEntity> find(Integer userid){
		
	return collection.findSome(userid);
		
	}
	
	public void cancel(Integer id) {
		
	collection.deleteById(id);
	}
}
