//
//  OptionsViewController.swift
//  Commune
//
//  Created by Mukul Surajiwale on 11/19/16.
//  Copyright © 2016 Mukul Surajiwale. All rights reserved.
//

import UIKit
import Firebase
import FBSDKLoginKit

class OptionsViewController: UIViewController {

	@IBOutlet weak var usernameLabel: UILabel!
	@IBOutlet weak var userEmailLabel: UILabel!
	
	var currentUserUID: String? = nil
	
    override func viewDidLoad() {
        super.viewDidLoad()
		self.navigationController?.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white]
        // Do any additional setup after loading the view.
		
		FIRAuth.auth()?.addStateDidChangeListener { auth, user in
			if user != nil {
				// user is already logged in
				if user?.displayName != nil {
					self.usernameLabel.text = user?.displayName
					self.userEmailLabel.text = user?.email
					self.currentUserUID = user?.uid
				} else {
					let usersRef = FIRDatabase.database().reference(withPath: "Users")
					usersRef.queryOrdered(byChild: "Email").queryEqual(toValue: user?.email).observeSingleEvent(of: .value, with: { snapshot in						
						if !snapshot.exists() {
							print("USER NOT FOUND")
						}
						
						for child in snapshot.children.allObjects as! [FIRDataSnapshot] {
							let dict = child.value as! NSDictionary
							let name = dict["Name"]!
							self.usernameLabel.text = name as? String
							self.userEmailLabel.text = dict["Email"] as? String
							self.currentUserUID = child.key
						}
						
					})
				}
				
			} else {
				// User is not logged in
				print("ERROR: No user is currently logged in. This is a Firebase related error.")
				// Go back to the login view
				let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
				let vc: UIViewController = storyboard.instantiateViewController(withIdentifier: "LoginViewController")
				self.present(vc, animated: true, completion: nil)
			}
		}
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
	
	@IBAction func logoutButtonPressed(_ sender: Any) {
		try! FIRAuth.auth()!.signOut()
		FBSDKLoginManager().logOut()
		let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
		let vc: UIViewController = storyboard.instantiateViewController(withIdentifier: "LoginViewController")
		self.present(vc, animated: true, completion: nil)
	}
	
	@IBAction func viewGroupsButtonPressed(_ sender: Any) {
		let vc = storyboard?.instantiateViewController(withIdentifier: "GroupsViewController") as! GroupsViewController
		vc.currentUserUID = self.currentUserUID!
		navigationController?.pushViewController(vc, animated: true)
		
	}
}
