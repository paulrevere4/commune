//
//  GroupsViewController.swift
//  Commune
//
//  Created by Mukul Surajiwale on 10/29/16.
//  Copyright © 2016 Mukul Surajiwale. All rights reserved.
//

import UIKit
import FBSDKLoginKit
import Firebase

class GroupsViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
	
	@IBOutlet weak var tableView: UITableView!
	
	var currentUserUID: String? = nil
	var items: [Group] = []

    override func viewDidLoad() {
        super.viewDidLoad()
		
		self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]

		
		print(currentUserUID!)
	
		let groupsRef = FIRDatabase.database().reference(withPath: "Users").child(currentUserUID!).child("Groups")
		
		groupsRef.observe(.value, with: { snapshot in
			
			var newItems: [Group] = []
			
			for child in snapshot.children.allObjects as! [FIRDataSnapshot] {
				print(child.key, child.value!)
				let groupItem = Group(name: child.value! as! String, groupID: child.key)
				newItems.append(groupItem)
			}
			
			self.items = newItems
			self.tableView.reloadData()
		})
		
		
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

	@IBAction func backButtonPressed(_ sender: Any) {
		let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
		let vc: UIViewController = storyboard.instantiateViewController(withIdentifier: "OptionsNavigationViewController")
		self.present(vc, animated: true, completion: nil)
	}
	
	// MARK - Table View Data Source
	func numberOfSections(in tableView: UITableView) -> Int {
		return 1
	}
	
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return items.count
	}
	
	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		let cell = tableView.dequeueReusableCell(withIdentifier: "GroupCell", for: indexPath)
	
		let group = items[indexPath.row]
		cell.textLabel?.text = group.name
		
		return cell
		
	}
	
	func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
		tableView.deselectRow(at: indexPath, animated: true)
		let row = indexPath.row
		
		let vc = storyboard?.instantiateViewController(withIdentifier: "GroupInfoViewController") as! GroupInfoViewController
		
		vc.groupName = items[row].name
		vc.groupID = items[row].groupID
		
		navigationController?.pushViewController(vc, animated: true)
		
	}
		

}
