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

class GroupsViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
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
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
