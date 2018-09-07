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
        floaty.addItem(title: "from camera", handler: { item in self.floatyCameraClick() })
        floaty.addItem(title: "from library")
        self.view.addSubview(floaty)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func floatyCameraClick() {
        let pickerController = UIImagePickerController()
        pickerController.sourceType = .camera
        present(pickerController, animated: true, completion: nil)
    }
}
