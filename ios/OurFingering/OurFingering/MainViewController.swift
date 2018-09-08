//
//  ViewController.swift
//  OurFingering
//
//  Created by NAKAGAKI Kenji on 9/2/18.
//  Copyright © 2018 IT Virtuoso. All rights reserved.
//

import UIKit
import Floaty

class MainViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let floaty = Floaty()
        floaty.addItem(icon: UIImage(named: "Camera"), handler: { item in self.floatyCameraClick() })
        floaty.addItem(icon: UIImage(named: "Library"), handler: { item in self.floatyLibraryClick() })
        self.view.addSubview(floaty)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func floatyCameraClick() {
        let picker = UIImagePickerController()
        picker.sourceType = .camera
        present(picker, animated: true, completion: nil)
    }
    
    func floatyLibraryClick() {
        let picker = UIImagePickerController()
        picker.sourceType = .photoLibrary
        present(picker, animated: true, completion: nil)
    }
}
