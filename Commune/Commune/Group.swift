//
//  Group.swift
//  Commune
//
//  Created by Mukul Surajiwale on 10/30/16.
//  Copyright © 2016 Mukul Surajiwale. All rights reserved.
//

import UIKit
import Firebase

class Group: NSObject {
	
	let name: String
	let createdBy: FIRUser
	var members: [String] = []
	var groupsRef = FIRDatabase.database().reference(withPath: "Groups")
	
	
	init(name: String, createdBy: FIRUser, members: Array<String>) {
		self.name = name
		self.createdBy = createdBy
		self.members = members
	}
	
	func addGroupToFirebase() -> Bool {
		print(members)
		
		// Add the group to Firebase
		let groupRef = groupsRef.childByAutoId()
		groupRef.setValue(["CreatorUid" : createdBy.uid, "Name" : name])
		groupRef.child("Members").setValue([createdBy.uid : createdBy.displayName])
		
		// Add the group to the user
		let usersRef = FIRDatabase.database().reference(withPath: "Users")
		usersRef.child(createdBy.uid).child("Groups").child(groupRef.key).setValue(["Name" : name])
		
		// Find if each member to add exists in the database. Get the users user id and then add them
		// as a memeber to the group.
		for member in members {
			usersRef.queryOrdered(byChild: "email").queryEqual(toValue: member).observe(.value, with: { snapshot in
				
				if !snapshot.exists() {
					print("USER NOT FOUND")
				}
				
				for child in snapshot.children.allObjects as! [FIRDataSnapshot] {
					print(child.key)
					let dict = child.value as! NSDictionary
					groupRef.child("Members").child(child.key).setValue(dict["name"]!)
					
				}
				
			})
		}
		
		
	
		return true
	}
	
}
