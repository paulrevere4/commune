//
//  ViewController.swift
//  Commune
//
//  Created by Mukul Surajiwale on 10/29/16.
//  Copyright © 2016 Mukul Surajiwale. All rights reserved.
//

import UIKit
import FBSDKLoginKit
import Firebase

class ViewController: UIViewController, FBSDKLoginButtonDelegate {
	
	// MARK: Outlets
	@IBOutlet weak var loginSegmentControl: UISegmentedControl!
	@IBOutlet weak var emailTextField: UITextField!
	@IBOutlet weak var passwordTextField: UITextField!
	@IBOutlet weak var nameTextField: UITextField!
	@IBOutlet weak var loginActionButton: UIButton!
	
	let loginButton: FBSDKLoginButton = FBSDKLoginButton()
	var usersRef: FIRDatabaseReference! = nil
	
	override func viewDidLoad() {
		super.viewDidLoad()
		usersRef = FIRDatabase.database().reference(withPath: "Users")
		
		loginButton.isHidden = true
		loginSegmentControl.isHidden = true
		emailTextField.isHidden = true
		passwordTextField.isHidden = true
		nameTextField.isHidden = true
		loginActionButton.isHidden = true
		
		FIRAuth.auth()?.addStateDidChangeListener { auth, user in
			if user != nil {
				// user is already logged in
				self.showGroupsView()
			} else {
				// User is not logged in
				self.loginButton.isHidden = false
				self.loginButton.readPermissions = ["public_profile", "email", "user_friends"]
				self.view.addSubview(self.loginButton)
				self.loginButton.frame = CGRect(x: -5, y: 592, width: self.view.frame.width + 25, height: 75)
				self.loginButton.delegate = self
			}
		}
	}
	
	
	
	// MARK: Actions
	@IBAction func loginSegmentControllerSelected(_ sender: Any) {
		// User wants to log in
		if loginSegmentControl.selectedSegmentIndex == 0 {
			emailTextField.isHidden = false
			passwordTextField.isHidden = false
			loginActionButton.frame.origin = CGPoint(x: 16, y: 333)
			loginActionButton.setTitle("Log in", for: UIControlState.normal)
			loginActionButton.isHidden = false
			nameTextField.isHidden = true
		// User wants to sign up
		} else if loginSegmentControl.selectedSegmentIndex == 1 {
			emailTextField.isHidden = false
			passwordTextField.isHidden = false
			nameTextField.isHidden = false
			loginActionButton.frame.origin = CGPoint(x: 16, y: 371)
			loginActionButton.isHidden = false
			loginActionButton.setTitle("Sign up", for: UIControlState.normal)
		}
		
	}
	
	@IBAction func loginActionButtonPressed(_ sender: Any) {
		// User is trying to log in using an existing account
		if loginActionButton.titleLabel?.text == "Log in" {
			print("LOG IN REQUESTED")
			// Try to log the user in
			FIRAuth.auth()!.signIn(withEmail: self.emailTextField.text!, password: self.passwordTextField.text!) { (user, error) in
				
				if error != nil {
					print(error.debugDescription)
					return
				}
				print("SUCCESSFULLY LOGGED IN")
				
				self.showGroupsView()
			}
		// User is registering a new account
		} else if loginActionButton.titleLabel?.text == "Sign up" {
			print("SIGN UP REQUESTED")
			
			// Try to create a new account for the user using the supplied credentials
			FIRAuth.auth()!.createUser(withEmail: self.emailTextField.text!, password: self.passwordTextField.text!) { (user, error) in
				// User successfully created
				if error == nil {
					let name: String = self.nameTextField.text!
					let email: String = self.emailTextField.text!
					
					self.usersRef.child((user?.uid)!).setValue(["Name": name, "Email": email])
					
					// Log the user in
					FIRAuth.auth()!.signIn(withEmail: self.emailTextField.text!, password: self.passwordTextField.text!) { (user, error) in
						// User successfully logged in
						if error == nil {
							self.showGroupsView()
						} else {
							print(error.debugDescription)
							return
						}
					}
				} else {
					print(error.debugDescription)
					return
				}
			
			}
		}
	}

	@IBAction func loginEmailButtonPressed(_ sender: Any) {
		loginSegmentControl.isHidden = false
		emailTextField.isHidden = false
		passwordTextField.isHidden = false
		loginActionButton.frame.origin = CGPoint(x: 16, y: 333)
		loginActionButton.setTitle("Log in", for: UIControlState.normal)
		loginActionButton.isHidden = false
	}

	
	
	// MARK: Helper Functions
	func loginButton(_ loginButton: FBSDKLoginButton!, didCompleteWith result: FBSDKLoginManagerLoginResult!, error: Error!) {
		if error != nil {
			print(error)
			return
		}
		
		// Log user in through Firebase
		let credential = FIRFacebookAuthProvider.credential(withAccessToken: FBSDKAccessToken.current().tokenString)
		FIRAuth.auth()?.signIn(with: credential) { (user, error) in
			
			if error == nil {
				// Store user info in database
				self.usersRef.child((user?.uid)!).setValue(["Name":user?.displayName , "Email": user?.email])
				// Display the Groups View once the user is logged in.
				self.showGroupsView()
			} else {
				return
			}
		}
	}
	
	func loginButtonDidLogOut(_ loginButton: FBSDKLoginButton!) {
		print("Did log out of facebook")
	}
	
	// Transition to the groups view
	func showGroupsView() {
		let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
		let vc: UINavigationController = storyboard.instantiateViewController(withIdentifier: "OptionsNavigationViewController") as! UINavigationController
		self.present(vc, animated: true, completion: nil)
	}
}

