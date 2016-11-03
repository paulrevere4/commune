//
//  NewGroupViewController.swift
//  Commune
//
//  Created by Mukul Surajiwale on 10/31/16.
//  Copyright © 2016 Mukul Surajiwale. All rights reserved.
//

import UIKit
import Firebase

class NewGroupViewController: UIViewController {
	
	// MARK: Properties
	@IBOutlet weak var groupNameLabel: UILabel!
	@IBOutlet weak var groupMembersTextView: UITextView!
	@IBOutlet weak var groupNameTextField: UITextField!
	@IBOutlet weak var groupDescriptionTextField: UITextField!
	@IBOutlet weak var groupMembersTextField: UITextField!
	
	var currentUser: FIRUser? = nil
	var usersRef: FIRDatabaseReference! = nil
	

    override func viewDidLoad() {
        super.viewDidLoad()
		
		self.currentUser = FIRAuth.auth()?.currentUser
		self.usersRef = FIRDatabase.database().reference(withPath: "Users")
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
	
	@IBAction func createGroupButtonPressed(_ sender: Any) {
		print("CREATE GROUP BUTTON PRESSED")
		
		print(self.groupNameTextField.text!)
		print(self.currentUser!.uid)
		
		let trimmed = self.groupMembersTextField.text?.replacingOccurrences(of: " ", with: "")
		
		let membersToAddArray = trimmed?.components(separatedBy: ",")
		
		let newGroup = Group(name: self.groupNameTextField.text!, createdBy: self.currentUser!, members: membersToAddArray!)
		newGroup.addGroupToFirebase()
		
		
	}

}
