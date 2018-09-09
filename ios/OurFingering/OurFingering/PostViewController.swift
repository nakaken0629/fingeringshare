//
//  PostViewController.swift
//  OurFingering
//
//  Created by NAKAGAKI Kenji on 9/9/18.
//  Copyright © 2018 IT Virtuoso. All rights reserved.
//

import UIKit

class PostViewController : UIViewController {
    var image: UIImage?
    
    @IBOutlet weak var photoImageView: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.photoImageView.image = image
    }
}
