package com.census.censusapp.dao;

import java.util.List;

import com.census.censusapp.entity.Member;
import com.census.censusapp.exception.AddEntryException;
import com.census.censusapp.exception.CreateTableException;
import com.census.censusapp.exception.UpdateEntryException;


public interface CensusDAO {
	public boolean addMember(Member member) throws AddEntryException, CreateTableException;
	public boolean editMember(Member member,String familyid,String memberid) throws UpdateEntryException;
	public boolean deleteMember(String familyid,String memberid);
//	public List<Member> getMembers(String familyid);
	public List<Member> getMembers(String familyid);
	public void printDetails(Member member);
}
