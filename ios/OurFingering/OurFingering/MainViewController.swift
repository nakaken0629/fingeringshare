//
//  ViewController.swift
//  OurFingering
//
//  Created by NAKAGAKI Kenji on 9/2/18.
//  Copyright © 2018 IT Virtuoso. All rights reserved.
//

import UIKit
import Floaty
import AWSAuthCore
import AWSAuthUI

class MainViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        if !AWSSignInManager.sharedInstance().isLoggedIn {
            AWSAuthUIViewController.presentViewController(with: self.navigationController!, configuration: nil, completionHandler: { (provider: AWSSignInProvider, error: Error?) in
                if error != nil {
                    print("Error occurred: \(String(describing: error))")
                } else {
                    // sign in successful.
                }
            })
        }
        
        let floaty = Floaty()
        floaty.addItem(icon: UIImage(named: "Camera"), handler: floatyCameraClick)
        floaty.addItem(icon: UIImage(named: "Library"), handler: floatyLibraryClick)
        self.view.addSubview(floaty)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    private func floatyCameraClick(item: FloatyItem) {
        let picker = UIImagePickerController()
        picker.sourceType = .camera
        picker.delegate = self
        present(picker, animated: true, completion: nil)
    }
    
    private func floatyLibraryClick(item: FloatyItem) {
        let picker = UIImagePickerController()
        picker.sourceType = .photoLibrary
        picker.delegate = self
        present(picker, animated: true, completion: nil)
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        picker.dismiss(animated: false)
        guard let image = info[UIImagePickerControllerOriginalImage] as? UIImage else {
            return
        }
        performSegue(withIdentifier: "Post", sender: image)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        guard let identifier = segue.identifier else {
            return
        }
        switch identifier {
        case "Post":
            if let viewController = (segue.destination as? UINavigationController)?.topViewController as? PostViewController {
                viewController.image = sender as? UIImage
            }
        default:
            break
        }
    }
}
