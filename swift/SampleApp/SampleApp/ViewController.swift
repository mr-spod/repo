//
//  ViewController.swift
//  SampleApp
//
//  Created by Sean O'Donnell on 12/20/17.
//  Copyright © 2017 Sean O'Donnell. All rights reserved.
//

import UIKit
import RxSwift
import BlocksKit

class ViewController: UIViewController {

    var vm: ViewModel
    let disposeBag = DisposeBag()
    
    init(viewModel: ViewModel) {
        self.vm = viewModel
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder aDecoder: NSCoder) {
        self.vm = ViewModel()
        super.init(coder: aDecoder)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.blue
        
        vm.bpiData.asObservable().skip(1)
            .subscribe(onNext: { [weak self] (newVal) in
            print("OBSERVATION")
                print(self?.vm.dateLabels())
                print(self?.vm.values())
        }).disposed(by: disposeBag)
        
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

