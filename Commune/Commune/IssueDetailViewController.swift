//
//  IssueDetailViewController.swift
//  Commune
//
//  Created by Mukul Surajiwale on 11/21/16.
//  Copyright © 2016 Mukul Surajiwale. All rights reserved.
//

import UIKit
import Firebase

class IssueDetailViewController: UIViewController {
	
	var issueID: String? = nil
	var groupID: String? = nil
	var assignedToUID: String? = nil
	
	@IBOutlet weak var issueDetailLabel: UILabel!
	@IBOutlet weak var issueNameLabel: UILabel!
	@IBOutlet weak var dueDateLabel: UILabel!
	@IBOutlet weak var assignedToLabel: UILabel!
	@IBOutlet weak var completeTaskButton: UIButton!
	
    override func viewDidLoad() {
        super.viewDidLoad()
		
		let issuesRef = FIRDatabase.database().reference(withPath: "Groups").child(groupID!).child("Issues")
		
		issuesRef.observeSingleEvent(of: .value, with: { snapshot in
			if !snapshot.exists() {
				print("ERROR: Issue not found.")
			} else {
				for child in snapshot.children.allObjects as! [FIRDataSnapshot] {
					if child.key == self.issueID {
						let dict = child.value! as! NSDictionary
						let innerDict = dict["AssignedTo"]! as! NSDictionary
						
						
						self.issueNameLabel.text = (dict["Name"]! as! String)
						self.issueDetailLabel.text = (dict["Description"]! as! String)
						self.dueDateLabel.text = (dict["DueDate"]! as! String)
						self.assignedToLabel.text = (innerDict["UserName"]! as! String)
						self.assignedToUID = (innerDict["UserID"]! as! String)
						
						let status = (dict["Completed"]! as! String)
						
						if status == "True" {
							self.completeTaskButton.isHidden = true
						}
					}
				}
			}
		})
		
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    


	@IBAction func completeIssueButtonPressed(_ sender: Any) {
		
		
		let groupIssuesRef = FIRDatabase.database().reference(withPath: "Groups").child(groupID!).child("Issues").child(self.issueID!)
		groupIssuesRef.updateChildValues(["Completed" : "True"])
		
		if self.assignedToLabel.text != "NA" {
			let userIssuesRef = FIRDatabase.database().reference(withPath: "Users").child(self.assignedToUID!).child("Issues").child(self.issueID!)
			userIssuesRef.updateChildValues(["Completed" : "True"])
		}
		
		let alert = UIAlertController(title: "Monetary Contribution", message: "If it cost anything to complete this issue, then enter the amount an press 'Yes'", preferredStyle: .alert)
		
		let addAction = UIAlertAction(title: "Yes", style: .default) { action in
			let valueTextField = alert.textFields![0]
			let value = Float(valueTextField.text!)
			let user = FIRAuth.auth()?.currentUser
			
			FIRDatabase.database().reference(withPath: "Groups").child(self.groupID!).child("MonetaryContributions").child((user?.uid)!).observeSingleEvent(of: .value, with: { (snapshot) in
				let contribution = snapshot.value as! Float
				FIRDatabase.database().reference(withPath: "Groups").child(self.groupID!).child("MonetaryContributions").child((user?.uid)!).setValue(value! + contribution)
			})
			
			if let navController = self.navigationController {
				navController.popViewController(animated: true)
			}
			
		}
		
		let cancelAction = UIAlertAction(title: "No", style: .default)
		
		alert.addTextField()
		alert.addAction(addAction)
		alert.addAction(cancelAction)
		
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
