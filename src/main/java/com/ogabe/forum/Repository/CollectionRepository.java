package com.ogabe.forum.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ogabe.forum.entity.CollectionEntity;
import com.ogabe.user.entity.UserVO;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Integer> {

	@Query(value = "SELECT * FROM collection_entity where userid=?",nativeQuery = true)
	List<CollectionEntity> findSome(Integer userid);
}
