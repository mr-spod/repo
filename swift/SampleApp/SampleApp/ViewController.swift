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
import Charts

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
        view.backgroundColor = UIColor.white
        
        let pickerView = DateRangePickerView(vm: vm)
        pickerView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(pickerView)
        
        let views: [String: Any] = ["picker": pickerView]
        
        view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-[picker(150)]-|", metrics: [:], views: views))
        view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-[picker(100)]-|", metrics: [:], views: views))
        
        vm.bpiData.asObservable().skip(1)
            .subscribe(onNext: { [weak self] (newVal) in
            print("OBSERVATION")
                print(self?.vm.dateLabels())
                print(self?.vm.values())
                print(self?.vm.graphData().dataSets[0].entryCount)
        }).disposed(by: disposeBag)
        
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
//    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
//        <#code#>
//    }
    
//    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
//        <#code#>
//    }

}

