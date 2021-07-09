/**
 * 
 */
package com.census.censusapp.controller;

import java.util.*;


import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.census.censusapp.entity.Member;
import com.census.censusapp.exception.MemberNotFoundException;
import com.census.censusapp.service.MemberService;

@RestController
public class MemberController {
	
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	private Boolean validateMemberList(List<Member> membersList) {
		try {
			ArrayList<String> errorMessages = new ArrayList<String>();
			for(int i=0; i<membersList.size(); i++)
			{
				Set<ConstraintViolation<Member>> violations = validator.validate(membersList.get(i));
				for (ConstraintViolation<Member> violation : violations) {
					errorMessages.add(violation.getMessage());
				}
			}
			if(errorMessages!=null)
			{
				for(int i=0; i<errorMessages.size(); i++)
				{
					System.out.println(errorMessages.get(i));
				}
				return false;
			}
		}
		catch(ConstraintViolationException e)
		{
//			throw new handleConstraintViolationException();
		}
		
		return true;
	}

	

	@Autowired
	private MemberService memberservice;

	@GetMapping("/members")
	List<Member> all() {
		return memberservice.findAllMembers();
	}

	@PostMapping("/members")
	List<Member> newMember(@Valid @RequestBody List<Member> membersList) {
//		validateMemberList(membersList);
		

		return memberservice.saveNewMember(membersList);
	}

	@GetMapping("/greeting")
	public String greetings() {
		return "Hello How are you?";
	}

	@GetMapping("/members/{id}")
	public Optional<List<Member>> getMembersByFamilyId(@PathVariable String id) {

		if(!id.isBlank())
		{
			Optional<List<Member>> familyList = Optional
					.of(memberservice.getMembersByFamilyId(id).orElseThrow(() -> new MemberNotFoundException(id)));
			return familyList;
		}
		System.out.println("family id is null");
		return null;
	}

	@PutMapping("/members/{id}")
	public List<Member> updateMembersByFamilyId(@PathVariable String id, @RequestBody List<Member> membersList) {
		
		if(validateMemberList(membersList)&& !id.isBlank()) {
		memberservice.updateMembersByFamilyId(membersList, id);
		return membersList;
		}
		return null;
	}

	@PutMapping("/members/relation/{id}")
	public List<Member> updateMembersByRelation(@PathVariable String id, @RequestBody List<String> relationList) {
		
         
		return memberservice.updateMembersByRelation(relationList, id);
	}

	@DeleteMapping("/members/remove")
	public void deleteMemberByName(@RequestParam(value = "familyId", required = true) String familyId,
			@RequestParam(value = "firstName", required = true) String firstName) {
		
		System.out.println(familyId + " .. " + firstName);
		memberservice.deleteMemberOfFamily(familyId, firstName);

	}

}
