//
//  ViewController.swift
//  SampleApp
//
//  Created by Sean O'Donnell on 12/20/17.
//  Copyright © 2017 Sean O'Donnell. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa
import BlocksKit
import Charts
import DateTools

class ViewController: UIViewController {
    
    var vm: PickerViewModel
    var graphVm: ViewModel
    let disposeBag = DisposeBag()
    
    init(viewModel: PickerViewModel) {
        self.vm = viewModel
        self.graphVm = ViewModel()
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder aDecoder: NSCoder) {
        self.vm = PickerViewModel()
        self.graphVm = ViewModel()
        super.init(coder: aDecoder)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.white
        
        let pickerView = DateRangePickerView(vm: vm)
        pickerView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(pickerView)
        
        pickerView.submitButton.rx.tap
            .filter({ [weak self] () -> Bool in
                let start = self?.vm.startDate.value
                let end = self?.vm.endDate.value
                return (end! as NSDate).days(from: start) > 0
            })
            .subscribe(onNext: { [weak self] in
                self?.graphVm.getBpiData(start: (self?.vm.startDate.value)!,
                                         end: (self?.vm.endDate.value)!)
            }).disposed(by: vm.disposeBag)
        
        graphVm.bpiData.asObservable().filter({
                return !$0.isEmpty
            })
            .subscribe(onNext: { [weak self] (data: [String: AnyObject]) in
                let graphVc = GraphViewController(viewModel: (self?.graphVm)!)
                self?.navigationController?.pushViewController(graphVc, animated: true)
            }).disposed(by: vm.disposeBag)
        
        let views: [String: Any] = ["picker": pickerView]
        
        view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[picker(300)]", metrics: [:], views: views))
        view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:[picker(250)]", metrics: [:], views: views))
        view.addConstraint(NSLayoutConstraint(item: pickerView, attribute: .centerX, relatedBy: .equal, toItem: view, attribute: .centerX, multiplier: 1.0, constant: 0.0))
        view.addConstraint(NSLayoutConstraint(item: pickerView, attribute: .centerY, relatedBy: .equal, toItem: view, attribute: .centerY, multiplier: 1.0, constant: -30))
    }

}

