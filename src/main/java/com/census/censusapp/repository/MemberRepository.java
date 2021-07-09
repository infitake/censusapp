/**
 * 
 */
package com.census.censusapp.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import com.census.censusapp.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Integer> {

	Optional<List<Member>> findByFamilyId(String familyId);
	@Transactional    
	@Modifying
	@Query(value="DELETE FROM Member WHERE family_id =?1 and first_name=?2",nativeQuery = true)
	public void deleteByFamilyIdAndFirstName(String familyId,String firstName);
}
