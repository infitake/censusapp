package com.census.censusapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.census.censusapp.entity.Member;
import com.census.censusapp.exception.AddEntryException;
import com.census.censusapp.exception.CreateTableException;
import com.census.censusapp.exception.UpdateEntryException;
import com.census.censusapp.utility.EntityManagerUtility;


public class CensusDaoJpaImpl implements CensusDAO{
	public CensusDaoJpaImpl() {
	}

	@Override
	public boolean addMember(Member member) throws AddEntryException, CreateTableException {
		EntityManager entityManager = EntityManagerUtility.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(member);
		entityManager.getTransaction().commit();
		entityManager.close();
		return true;
	}

	@Override
	public boolean editMember(Member member, String familyid, String memberid) throws UpdateEntryException {
		EntityManager entityManager = EntityManagerUtility.getEntityManager();
		entityManager.getTransaction().begin();
		int result = entityManager.createQuery("Update Member set firstname=:firstname  where memberid=:memberid")
				.setParameter("firstname", member.getFirstName())
				.setParameter("memberid",member.getId()).executeUpdate();
		System.out.println(result);
		entityManager.getTransaction().commit();
		entityManager.close(); 
		return false;
	}

	@Override
	public boolean deleteMember(String familyid, String memberid) {
		EntityManager entityManager = EntityManagerUtility.getEntityManager();
		Member member = entityManager.find(Member.class, memberid);
		if (member != null) {
			entityManager.getTransaction().begin();
			entityManager.remove(member);
			entityManager.getTransaction().commit();
			entityManager.close();
			return true;
		}
		return false;
	}

	@Override
	public List<Member> getMembers(String familyid) {
		EntityManager entityManager = EntityManagerUtility.getEntityManager();
		entityManager.getTransaction().begin();
		TypedQuery<Member> query = entityManager.createQuery("Select e from Member e", Member.class);
		List<Member> members = query.getResultList();
		entityManager.close();
		return members;
		
	}

	@Override
	public void printDetails(Member member) {
		System.out.println("FamilyId: "+member.getFamilyId());
		System.out.println("MemberId:  "+member.getId());
		System.out.println("FirstName: "+member.getFirstName());
		System.out.println("LastName:  "+member.getLastName());
		System.out.println("Gender:  "+member.getGender());
		System.out.println("DOB: "+member.getDob().toString());
		
	}

}
