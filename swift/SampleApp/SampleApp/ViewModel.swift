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
    public var startDate: Date
    private let bag = DisposeBag()
    
    override init() {
        self.bpiData = Variable([:])
        self.startDate = Date()
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
        self.startDate = fromDate
        api.bpiHistoricalData(fromDate: fromDate, toDate: toDate)
            .subscribe(onNext: { [weak self] (request) in
            request.responseJSON { (json) -> Void in
                if let jsonObject = json.result.value as? [String: AnyObject] {
                    self?.bpiData.value = jsonObject
                }
            }
        }).disposed(by: bag)
    }
    
    public func dateLabels() -> [String] {
        var index = 0
        let daysFromStart = NSDate().days(from: startDate)
        var indexDate = (startDate as NSDate).copy() as! NSDate
        var labels = [String?](repeating: "", count: daysFromStart)
        while (index != daysFromStart) {
            labels[index] = ViewModel.dateKey(date: (indexDate as Date))
            indexDate = (indexDate.addingDays(1) as NSDate)
            index += 1
        }
        return labels as! [String]
    }
    
    public func values() -> [NSNumber] {
        let labels = dateLabels()
        var vals = [NSNumber?](repeating: NSNumber(), count: labels.count)
        var index = 0
        let bpiMap = bpiDateMap()
        while (index < labels.count) {
            vals[index] = bpiMap.value(forKey: labels[index]) as! NSNumber
            index += 1
        }
        return vals as! [NSNumber]
    }
    
    public func bpiDateMap() -> NSDictionary {
        guard let bpiDateMap = bpiData.value["bpi"] as? NSDictionary else {
            print("ERROR: no value in bpiData for key 'bpi'")
            return [:]
        }
        let today = Date()
        var index = (startDate as NSDate).days(from: today)
        while (index > 0) {
            var indexDate = (today as NSDate)
            indexDate = (indexDate.subtractingDays(index) as NSDate)
            index -= 1
            let key = ViewModel.dateKey(date: (indexDate as Date))
            let price = bpiDateMap.object(forKey: key)
            guard let floatPrice = price as? Float else {
                print("ERROR: couldn't read bpi price as float")
                return [:]
            }
            bpiDateMap.setValue(NSNumber(value: floatPrice), forKey: key)
        }
        return bpiDateMap
    }
    
    public static func dateKey(date: Date) -> String {
        return date.description.substring(to: String.Index(10))
    }
    
}
