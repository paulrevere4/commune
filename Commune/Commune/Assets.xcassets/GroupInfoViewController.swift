//
//  GroupInfoViewController.swift
//  Commune
//
//  Created by Mukul Surajiwale on 11/19/16.
//  Copyright © 2016 Mukul Surajiwale. All rights reserved.
//

import UIKit
import Firebase

class GroupInfoViewController: UIViewController {
	
	var groupName: String? = nil
	var groupID: String? = nil
	
	
	@IBOutlet weak var groupNameLabel: UILabel!
	@IBOutlet weak var groupIDLabel: UILabel!
	
    override func viewDidLoad() {
        super.viewDidLoad()
		
        // Do any additional setup after loading the view.
		groupNameLabel.text = groupName
		groupIDLabel.text = groupID
		
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
	@IBAction func showMembersButtonPressed(_ sender: Any) {
		let vc = storyboard?.instantiateViewController(withIdentifier: "GroupMembersViewController") as! GroupMembersTableViewController
		
		vc.groupID = self.groupID
		vc.groupName = self.groupName
		
		navigationController?.pushViewController(vc, animated: true)
	
	}

	@IBAction func showIssuesButtonPressed(_ sender: Any) {
		let vc = storyboard?.instantiateViewController(withIdentifier: "IssuesViewController") as! IssuesTableViewController
		
		vc.groupID = self.groupID
		navigationController?.pushViewController(vc, animated: true)
	}
	
	@IBAction func showResourcesButtonPressed(_ sender: Any) {
		let vc = storyboard?.instantiateViewController(withIdentifier: "ResourcesViewController") as! ResourceTableViewController
		
		vc.groupID = self.groupID
		navigationController?.pushViewController(vc, animated: true)
	}
	
	@IBAction func leaveGroupButtonPressed(_ sender: Any) {
		let user = FIRAuth.auth()?.currentUser
		
		let userGroupsRef = FIRDatabase.database().reference(withPath: "Users").child((user?.uid)!).child("Groups")
		userGroupsRef.child(self.groupID!).removeValue()
		
		let groupMembers = FIRDatabase.database().reference(withPath: "Groups").child(self.groupID!).child("Members")
		groupMembers.observeSingleEvent(of: .value, with: { snapshot in
			for child in snapshot.children.allObjects as! [FIRDataSnapshot] {
				if child.key == user?.uid {
					groupMembers.child(child.key).removeValue()
				}
			}
		})
		
		navigationController?.popViewController(animated: true)
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
