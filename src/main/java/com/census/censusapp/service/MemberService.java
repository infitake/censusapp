package com.census.censusapp.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.census.censusapp.entity.Member;
import com.census.censusapp.exception.MemberNotFoundException;
import com.census.censusapp.repository.MemberRepository;


@Service
public class MemberService {
	@Autowired
	private MemberRepository memberrepository;

	public List<Member> findAllMembers() {
		return  memberrepository.findAll();
		
	}

	public List<Member> saveNewMember(List<Member> membersList) {
		for(int i=0;i<membersList.size();i++)
		{
			System.out.println(membersList.get(i).getFirstName());
		}
		
		String familyId= UUID.randomUUID().toString();
		
		for(int i=0;i<membersList.size();i++)
		{
			if (i==0)
			{
				membersList.get(i).setIsHead(true);
				membersList.get(i).setRelationBetween("self");
			}else
			{
				membersList.get(i).setIsHead(false);
				
			}
			membersList.get(i).setFamilyId(familyId);
		}
		memberrepository.saveAll(membersList);
		return membersList;
	}

	public Optional <List <Member>> getMembersByFamilyId(String familyId) {
		Optional<List<Member>> member =memberrepository.findByFamilyId(familyId);
		return member;
	}
	public List<Member> updateMembersByFamilyId(List<Member> membersList,String id) {
		List<Member> member =memberrepository.findByFamilyId(id).orElseThrow( ()-> new MemberNotFoundException(id));
		
		for(int i=0;i<membersList.size();i++)
		{
			member.get(i).setFirstName(membersList.get(i).getFirstName());
			member.get(i).setLastName(membersList.get(i).getLastName());
			member.get(i).setMiddleName(membersList.get(i).getMiddleName());
			member.get(i).setSuffix(membersList.get(i).getSuffix());
			member.get(i).setDob(membersList.get(i).getDob());
			member.get(i).setGender(membersList.get(i).getGender());
			member.get(i).setRelationBetween(membersList.get(i).getRelationBetween());

		}
		return memberrepository.saveAll(member);
		
	}

	public  List<Member> updateMembersByRelation(List<String> relationList, String id) {
		List<Member> member =memberrepository.findByFamilyId(id).orElseThrow( ()-> new MemberNotFoundException(id));
		member.get(0).setRelationBetween("self");
		for(int i=0;i<relationList.size();i++)
		{
 
				member.get(i+1).setRelationBetween(relationList.get(i));

		}
		return memberrepository.saveAll(member);
	}
	
	public void deleteMemberOfFamily(String familyId,String firstName) {
        memberrepository.deleteByFamilyIdAndFirstName(familyId,firstName);
	}

	
}
