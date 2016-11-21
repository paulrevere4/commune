
//
//  ResourceTableViewController.swift
//  Commune
//
//  Created by Mukul Surajiwale on 11/21/16.
//  Copyright © 2016 Mukul Surajiwale. All rights reserved.
//

import UIKit
import Firebase

class ResourceTableViewController: UITableViewController {
	
	var groupID: String? = nil
	var resources: [Resource] = []

    override func viewDidLoad() {
        super.viewDidLoad()
		
		let resourceRef = FIRDatabase.database().reference(withPath: "Groups").child(self.groupID!).child("Resources")
		
		resourceRef.observe(.value, with: { snapshot in
		
			var newResources: [Resource] = []
			
			for child in snapshot.children.allObjects as! [FIRDataSnapshot] {
				let dict = child.value! as! NSDictionary
				let newResource = Resource(name: dict.value(forKey: "Name") as! String, details: dict.value(forKey: "Details") as! String, groupID: self.groupID!, resourceID: child.key)
				newResources.insert(newResource, at: 0)
			}
			
			self.resources = newResources
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
        return resources.count
    }

	@IBAction func addResourceButtonPressed(_ sender: Any) {
		let alert = UIAlertController(title: "New Resource", message: "Enter Resource name and description.", preferredStyle: .alert)
		
		let addAction = UIAlertAction(title: "Add", style: .default) { action in
			let resourceName = alert.textFields![0] as UITextField
			let resourceDescription = alert.textFields![1] as UITextField
			
			let resourceRef = FIRDatabase.database().reference(withPath: "Groups").child(self.groupID!).child("Resources")
			resourceRef.childByAutoId().setValue(["Name" : resourceName.text, "Details" : resourceDescription.text])
			
		}
		
		let cancelAction = UIAlertAction(title: "Cancel", style: .default)
		
		alert.addTextField()
		alert.addTextField()
		alert.addAction(addAction)
		alert.addAction(cancelAction)
		
		present(alert, animated: true, completion: nil)
	}
	
	
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ResourceCell", for: indexPath)

        let resource = self.resources[indexPath.row]
		
		cell.textLabel?.text = resource.name
		cell.detailTextLabel?.text = resource.details

        return cell
    }
	
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
			
			let resource = resources[indexPath.row]
			let resourceRef = FIRDatabase.database().reference(withPath: "Groups").child(self.groupID!).child("Resources")
			resourceRef.child(resource.resourceID!).removeValue()
		
			
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }

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
