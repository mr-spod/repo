//
//  PickerViewModel.swift
//  SampleApp
//
//  Created by Sean O'Donnell on 12/24/17.
//  Copyright © 2017 Sean O'Donnell. All rights reserved.
//

import Foundation
import RxSwift

class PickerViewModel: NSObject {
    let startDate = Variable<Date>(Date())
    let endDate = Variable<Date>(Date())
    let disposeBag = DisposeBag()
    
    func setStartDate(d: Date) {
        startDate.value = d
        print("Start Date : " + d.description)
    }
    
    func setEndDate(d: Date) {
        endDate.value = d
        print("End Date : " + d.description)
    }
    
    override init() {
        super.init()
    }
}
