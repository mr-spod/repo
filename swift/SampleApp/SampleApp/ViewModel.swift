//
//  ViewModel.swift
//  SampleApp
//
//  Created by Sean O'Donnell on 12/22/17.
//  Copyright © 2017 Sean O'Donnell. All rights reserved.
//

import RxSwift
import Foundation
import DateTools

class ViewModel: NSObject {
    
    public var bpiData: Variable<[String: AnyObject]>
    private let bag = DisposeBag()
    
    override init() {
        self.bpiData = Variable([:])
        super.init()
        print(ViewModel.configDictionary())
    }
    
    public static func configDictionary() -> Dictionary<String, String> {
        var config = Dictionary<String, String>()
        if let path = Bundle.main.path(forResource: "config", ofType: "json") {
            do {
                let data = try Data(contentsOf: URL(fileURLWithPath: path), options: .mappedIfSafe)
                let jsonResult = try JSONSerialization.jsonObject(with: data, options: .mutableLeaves)
                if let jsonResult = jsonResult as? Dictionary<String, String> {
                    config = jsonResult
                }
            } catch {
                
            }
        }
        return config
    }
    
    public func defaultBpiData() {
        let today = NSDate()
        let sixMonthsAgo = today.subtractingMonths(6)
        setBpiData(fromDate: sixMonthsAgo!, toDate: today as Date)
    }
    
    public func setBpiData(fromDate: Date, toDate: Date) {
        let api = Api()
        api.bpiHistoricalData(fromDate: fromDate, toDate: toDate).subscribe(onNext: { [weak self] (request) in
            request.responseJSON { (json) -> Void in
                if let jsonObject = json.result.value as? [String: AnyObject] {
                    print(jsonObject["bpi"] as! String)
                    self?.bpiData.value = jsonObject
                }
            }
        }).disposed(by: bag)
    }
    
}
