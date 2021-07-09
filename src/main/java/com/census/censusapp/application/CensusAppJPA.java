package com.census.censusapp.application;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import com.census.censusapp.dao.CensusDAO;
import com.census.censusapp.dao.CensusDaoJpaImpl;
import com.census.censusapp.entity.Member;
import com.census.censusapp.exception.DbConnectionErrorException;
import com.census.censusapp.exception.LengthExcedeException;
import com.census.censusapp.exception.UpdateEntryException;
import com.census.censusapp.exception.WrongInfoException;




public class CensusAppJPA {
	public static void main(String[] args) throws WrongInfoException, LengthExcedeException, DbConnectionErrorException, ClassNotFoundException, SQLException {
		String familyid;
		List<Member> members;
//		CensusRestController restcontroler ;
//		CensusServices censusService = new CensusServicesImpl();
		CensusDAO censusDao = new CensusDaoJpaImpl();
//		CensusOperation operation = new CensusOperation();
		while (true) {
			System.out.print("1:AddMember :2:EditMember :3:SearchMember :4:DeleteMember :");
			Scanner sc = new Scanner(System.in);
			int operation = sc.nextInt();
			switch(operation) {
			case 1:
				System.out.print("Enter Number of Members in the Family :");
				int count_of_member = sc.nextInt();
				familyid = "familyid" + count_of_member;
				boolean result = false;
				for (int i = 0; i < count_of_member; i++) {
					Member member = new Member();
					result = false;
					try {
						System.out.print("Enter the First Name :");
						String fname = sc.next();
						member.setFirstName(fname);

						System.out.print("Enter the Last Name :");
						String lname = sc.next();
						member.setLastName(lname);

						System.out.print("Enter the Gender :");
						String gender = sc.next();
						member.setGender(gender);

						System.out.print("Enter the dob :");
						String dob = sc.next();
//						member.setDob(dob);

						System.out.print("Enter if head or not :");
						boolean ishead = sc.nextBoolean();
						member.setIsHead(ishead);

						String memberid = fname + lname + dob;
//						member.setid(memberid);
						member.setFamilyId(familyid);
						System.out.print("member is"+member);
						result = censusDao.addMember(member);
					} catch (Exception e) {
						System.out.print("hello" + e);
					}
					if(result) System.out.print("Succesfully added");
				}
				break;
			case 2:
				Member member = new Member();
				System.out.print("Enter your Family id ");
				familyid = sc.next();
				System.out.print("Enter your member id");
				String memberid = sc.next();
				System.out.print("Enter the First Name :");
				String fname = sc.next();
				member.setFirstName(fname);

				System.out.print("Enter the Last Name :");
				String lname = sc.next();
				member.setFirstName(lname);

				System.out.print("Enter the Gender :");
				String gender = sc.next();
				member.setGender(gender);

				System.out.print("Enter the dob :");
				String dob = sc.next();
//				member.setDob(dob);
//				member.setMemberid(memberid);
				member.setFamilyId(familyid);
				System.out.print("Enter if head or not :");
				boolean ishead = sc.nextBoolean();
				member.setIsHead(ishead);
				try {
					result = censusDao.editMember(member,familyid,memberid);
				} catch (UpdateEntryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			case 3:
				System.out.print("Enter your Family and Member id to get Result  :");
				familyid = sc.next();
//				members = censusService.getMembers(familyid);

				members = censusDao.getMembers(familyid);
				members.forEach((mem) -> {
					censusDao.printDetails(mem);
				});
				break;
			case 4:
				System.out.print("Enter your Family id ");
				familyid = sc.next();
				System.out.print("Enter your member id");
				memberid = sc.next();
				if(censusDao.deleteMember(familyid, memberid))
					System.out.print("Successfully deleted \n");
				
			default:
				System.out.print("Enter right Values");
			}
		}
	}
}
