//
//  PostViewController.swift
//  OurFingering
//
//  Created by NAKAGAKI Kenji on 9/9/18.
//  Copyright © 2018 IT Virtuoso. All rights reserved.
//

import UIKit

class PostViewController : UIViewController {
    var photo: UIImage?
    private var post: Post?
    
    @IBOutlet weak var scrollView: UIScrollView!
    @IBOutlet weak var photoImageView: UIImageView!
    @IBOutlet weak var instrumentTextField: UITextField!
    @IBOutlet weak var composerTextField: UITextField!
    @IBOutlet weak var titleTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.textFieldInit()
        self.photoImageView.image = photo
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(keyboardWillBeShown(notification:)),
                                               name: NSNotification.Name.UIKeyboardWillShow,
                                               object: nil)
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(keyboardWillBeHidden(notification:)),
                                               name: NSNotification.Name.UIKeyboardWillHide,
                                               object: nil)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        NotificationCenter.default.removeObserver(self,
                                                  name: NSNotification.Name.UIKeyboardWillShow,
                                                  object: nil)
        NotificationCenter.default.removeObserver(self,
                                                  name: NSNotification.Name.UIKeyboardWillHide,
                                                  object: nil)
    }
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        switch identifier {
        case "Share":
            guard let photo = self.photoImageView.image else {
                return false
            }
            guard let instrument = self.instrumentTextField.text else {
                return false
            }
            guard let composer = self.composerTextField.text else {
                return false
            }
            guard let title = self.titleTextField.text else {
                return false
            }
            self.post = Post(photo: photo, instrument: instrument, composer: composer, title: title)
            return true
        default:
            return true
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        guard let identifier = segue.identifier else {
            return
        }
        switch identifier {
        case "Share":
            if let viewController = segue.destination as? MainViewController {
                   viewController.post = self.post
            } else {
                assertionFailure("There may be mistakes in a storyboard.")
            }
        default:
            break
        }
    }
}

extension PostViewController : UITextFieldDelegate {
    func textFieldInit() {
        self.instrumentTextField.delegate = self
        self.composerTextField.delegate = self
        self.titleTextField.delegate = self
    }
    
    @objc func keyboardWillBeShown(notification: NSNotification) {
        if let userInfo = notification.userInfo {
            if let keyboardFrame = (userInfo[UIKeyboardFrameEndUserInfoKey] as AnyObject).cgRectValue {
                restoreScrollViewSize()
                let convertedKeyboardFrame = scrollView.convert(keyboardFrame, from: nil)
                let offsetY: CGFloat = self.titleTextField.frame.maxY - convertedKeyboardFrame.minY
                if offsetY < 0 { return }
                updateScrollViewSize(offsetY)
            }
        }
    }
    
    @objc func keyboardWillBeHidden(notification: NSNotification) {
        restoreScrollViewSize()
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    func updateScrollViewSize(_ offsetY: CGFloat) {
        let contentInsets = UIEdgeInsetsMake(0, 0, offsetY, 0)
        self.scrollView.contentInset = contentInsets
        self.scrollView.scrollIndicatorInsets = contentInsets
        self.scrollView.contentOffset = CGPoint(x: 0, y: offsetY)
    }
    
    func restoreScrollViewSize() {
        self.scrollView.contentInset = UIEdgeInsets.zero
        self.scrollView.scrollIndicatorInsets = UIEdgeInsets.zero
    }
}
