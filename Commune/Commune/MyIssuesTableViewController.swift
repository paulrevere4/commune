//
//  MyIssuesTableViewController.swift
//  Commune
//
//  Created by Mukul Surajiwale on 11/21/16.
//  Copyright © 2016 Mukul Surajiwale. All rights reserved.
//

import UIKit
import Firebase

class MyIssuesTableViewController: UITableViewController {
	
	let user = FIRAuth.auth()?.currentUser
	var issues: [Issue] = []
	
    override func viewDidLoad() {
        super.viewDidLoad()
		
//		FIRDatabase.database().reference().child("Users").child((self.user?.uid)!).child("Issues").observe(.value, with: { snapshot in
//			print(snapshot)
//		})
		let issuesRef = FIRDatabase.database().reference(withPath: "Users").child((self.user?.uid)!).child("Issues")
		issuesRef.observe(.value, with: { snapshot in
			
			var newIssues: [Issue] = []
			
			for child in snapshot.children.allObjects as! [FIRDataSnapshot] {
				let dict = child.value! as! NSDictionary
				if dict.count < 5 {
					continue
				} else {
					if dict["DueDate"] != nil {
						if dict["AssignedTo"] != nil {
							// Has name, description, due date, and assigned user
							let userDict = dict["AssignedTo"] as! NSDictionary
							
							let user = User(uid: userDict.value(forKey: "UserID") as! String, name: userDict.value(forKey: "UserName") as! String)
							let newIssue = Issue(name: dict.value(forKey: "Name") as! String, description: dict.value(forKey: "Description") as! String,
							                     dueDate: dict.value(forKey: "DueDate") as! String, assignedTo: user, issueID: child.key,
							                     completed: dict.value(forKey: "Completed") as! String, groupID: dict.value(forKey: "GroupID") as! String)
							newIssues.insert(newIssue, at: 0)
						} else {
							// Has name, description, and due date
							let newIssue = Issue(name: dict.value(forKey: "Name") as! String, description: dict.value(forKey: "Description") as! String,
							                     dueDate: dict.value(forKey: "DueDate") as! String, issueID: child.key,
							                     completed: dict.value(forKey: "Completed") as! String, groupID: dict.value(forKey: "GroupID") as! String)
							newIssues.insert(newIssue, at: 0)
						}
					} else {
						if dict["AssignedTo"] != nil {
							// Has name, description, and an assinged user
							let userDict = dict["AssignedTo"] as! NSDictionary
							let user = User(uid: userDict.value(forKey: "UserID") as! String, name: userDict.value(forKey: "UserName") as! String)
							let newIssue = Issue(name: dict.value(forKey: "Name") as! String, description: dict.value(forKey: "Description") as! String,
							                     assignedTo: user, issueID: child.key, completed: dict.value(forKey: "Completed") as! String, groupID: dict.value(forKey: "GroupID") as! String)
							newIssues.insert(newIssue, at: 0)
						} else {
							// Has name and description
							let newIssue = Issue(name: dict.value(forKey: "Name") as! String, description: dict.value(forKey: "Description") as! String,
							                     issueID: child.key, completed: dict.value(forKey: "Completed") as! String, groupID: dict.value(forKey: "GroupID") as! String)
							newIssues.insert(newIssue, at: 0)
						}
					}
				}
			}
			
			self.issues = newIssues
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
        return issues.count
    }

	
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "IssueCell", for: indexPath)

		let issue = issues[indexPath.row]
		
		if issue.completed == "True" {
			cell.textLabel?.textColor = UIColor.lightGray
			cell.detailTextLabel?.textColor = UIColor.lightGray
		} else {
			cell.textLabel?.textColor = UIColor.black
			cell.detailTextLabel?.textColor = UIColor.black
		}
		
		
		cell.textLabel?.text = issue.name
		cell.detailTextLabel?.text = issue.desc

        return cell
    }
	
	override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
		tableView.deselectRow(at: indexPath, animated: true)
		let row = indexPath.row
		
		let vc = storyboard?.instantiateViewController(withIdentifier: "IssueDetailViewController") as! IssueDetailViewController
		
		let issue = issues[row]
		
		vc.issueID = issue.issueID
		vc.groupID = issue.groupID
		
		navigationController?.pushViewController(vc, animated: true)
		
	}
	


    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
