//
//  ViewController.swift
//  OurFingering
//
//  Created by NAKAGAKI Kenji on 9/2/18.
//  Copyright © 2018 IT Virtuoso. All rights reserved.
//

import UIKit

import AWSAuthCore
import AWSAuthUI
import AWSCore
import AWSS3
import Floaty

class MainViewController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    var post: Post?
    
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
                viewController.photo = sender as? UIImage
            } else {
                assertionFailure("There may be mistakes in a storyboard.")
            }
        default:
            break
        }
    }
    
    @IBAction func cancelPost(segue: UIStoryboardSegue) {
        // nop
    }
    
    @IBAction func sharePost(segue: UIStoryboardSegue) {
        guard let post = self.post else {
            assertionFailure("self.post must be non-null")
            return
        }
        guard let data = UIImagePNGRepresentation(post.photo) else {
            assertionFailure("Cannot create png from photo")
            return
        }
        let bucket = "ourfingering-userfiles-mobilehub-1631323487"
        let key = "public/\(UUID().uuidString).png"
        let contentType = "image/png"
        let expression = AWSS3TransferUtilityUploadExpression()
        expression.progressBlock = {(task, progress) in
            DispatchQueue.main.async(execute: {
                // Do something e.g. Update a progress bar.
            })
        }
        
        var completionHandler: AWSS3TransferUtilityUploadCompletionHandlerBlock?
        completionHandler = { (task, error) -> Void in
            DispatchQueue.main.async(execute: {
                // Do something e.g. Alert a user for transfer completion.
                // On failed uploads, `error` contains the error object.
            })
        }
        
        let transferUtility = AWSS3TransferUtility.default()
        transferUtility.uploadData(data, bucket: bucket, key: key, contentType: contentType, expression: expression, completionHandler: completionHandler).continueWith {
            (task) -> AnyObject? in
            if let error = task.error {
                print("Error: \(error.localizedDescription)")
            }
            
            if let _ = task.result {
                // Do something with uploadTask.
            }
            return nil;
        }
    }
}
