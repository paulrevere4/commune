//
//  GroupMembersTableViewController.swift
//  Commune
//
//  Created by Mukul Surajiwale on 11/19/16.
//  Copyright © 2016 Mukul Surajiwale. All rights reserved.
//

import UIKit
import Firebase

class GroupMembersTableViewController: UITableViewController {

	var groupID: String? = nil
	var groupName: String? = nil
	var members: [User] = []
	var errorCount: Int = 0
	let usersRef = FIRDatabase.database().reference(withPath: "Users")
	
    override func viewDidLoad() {
        super.viewDidLoad()
		
		let groupRef = FIRDatabase.database().reference(withPath: "Groups").child(groupID!).child("Members")
		
		groupRef.observe(.value, with: { snapshot in
			
			var newMembers: [User] = []
			
			for child in snapshot.children.allObjects as! [FIRDataSnapshot] {
				print(child.key, child.value!)
				let newMember = User(uid: child.key, name: child.value as! String)
				newMembers.append(newMember)
			}
			
			self.members = newMembers
			self.tableView.reloadData()
		})

	
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return members.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "GroupMemberCell", for: indexPath)

		let member = members[indexPath.row]
		cell.textLabel?.text = member.name!
		
        return cell
    }

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

	
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            let member = members[indexPath.row]
			let groupMembersRef = FIRDatabase.database().reference(withPath: "Groups").child(groupID!).child("Members").child(member.uid!)
			groupMembersRef.removeValue()
			let userGroupsRef = FIRDatabase.database().reference(withPath: "Users").child(member.uid!).child("Groups").child(self.groupID!)
			userGroupsRef.removeValue()
			
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
	

	@IBAction func addMemberButtonPressed(_ sender: Any) {
		let alert = UIAlertController(title: "Add Member", message: "Enter users email address", preferredStyle: .alert)
		
		let addAction = UIAlertAction(title: "Add", style: .default) { action in
			let memberEmail = alert.textFields![0]
			
			self.usersRef.observeSingleEvent(of: .value, with: { (snapshot) in
				
				for child in snapshot.children.allObjects as! [FIRDataSnapshot] {
					let dict = child.value as! NSDictionary
					if dict["Email"] != nil {
						if dict["Email"] as! String == memberEmail.text! {
							FIRDatabase.database().reference(withPath: "Groups").child(self.groupID!).child("Members").child(child.key).setValue(dict["Name"]!)
							FIRDatabase.database().reference(withPath: "Users").child(child.key).child("Groups").child(self.groupID!).setValue(self.groupName!)
							FIRDatabase.database().reference(withPath: "Groups").child(self.groupID!).child("MonetaryContributions").child(child.key).setValue(0.0)
							return
						}
					}
				}
				
				self.showErrorAlert(error: "User does not exist")
				return
			})
		}
		
		let cancelAction = UIAlertAction(title: "Cancel", style: .default)
		
		alert.addTextField()
		alert.addAction(addAction)
		alert.addAction(cancelAction)
		
		present(alert, animated: true, completion: nil)
	}
	
	func showErrorAlert(error: String) -> Void {
		let alert = UIAlertController(title: "Error", message: error, preferredStyle: .alert)
		let okAction = UIAlertAction(title: "Ok", style: .default)
		alert.addAction(okAction)
		present(alert, animated: true, completion: nil)
	}

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
