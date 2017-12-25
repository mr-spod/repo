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
import Charts

class ViewModel: NSObject {
    
    public var bpiData: Variable<[String: AnyObject]>
    public var startDate: Date
    public var endDate: Date
    private let bag = DisposeBag()
    
    override init() {
        self.bpiData = Variable([:])
        self.startDate = Date()
        self.endDate = Date()
        super.init()
    }
    
    public static func configDictionary() -> Dictionary<String, AnyObject> {
        var config = Dictionary<String, AnyObject>()
        if let path = Bundle.main.path(forResource: "config", ofType: "json") {
            do {
                let data = try Data(contentsOf: URL(fileURLWithPath: path), options: .mappedIfSafe)
                let jsonResult = try JSONSerialization.jsonObject(with: data, options: .mutableLeaves)
                if let jsonResult = jsonResult as? Dictionary<String, AnyObject> {
                    config = jsonResult
                }
            } catch {
                
            }
        }
        return config
    }
    
    public func getBpiData(start: Date, end: Date) {
        startDate = start
        endDate = end
        let api = Api()
        api.bpiHistoricalData(fromDate: startDate, toDate: endDate)
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
        let daysFromStart = (endDate as NSDate).days(from: startDate)
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
            vals[index] = bpiMap[labels[index]] as? NSNumber
            index += 1
        }
        return vals as! [NSNumber]
    }
    
    public func bpiDateMap() -> [String: NSNumber] {
        guard var bpiDateMap = bpiData.value["bpi"] as? [String: AnyObject] else {
            print("ERROR: no value in bpiData for key 'bpi'")
            return [:]
        }
        var index = (endDate as NSDate).days(from: startDate)
        while (index > 0) {
            var indexDate = (endDate as NSDate)
            indexDate = (indexDate.subtractingDays(index) as NSDate)
            index -= 1
            let key = ViewModel.dateKey(date: (indexDate as Date))
            let price = bpiDateMap[key]
            guard let floatPrice = price as? Float else {
                print("ERROR: couldn't read bpi price as float")
                return [:]
            }
            bpiDateMap[key] = NSNumber(value: floatPrice)
        }
        return bpiDateMap as! [String: NSNumber]
    }
    
    public func graphData() -> LineChartData {
        let values = self.values()
        var chartEntries = [ChartDataEntry]()
        var index = 0
        for n: NSNumber in  values {
            let entry = ChartDataEntry.init(x: Double(index), y: n.doubleValue)
            chartEntries.append(entry)
            index += 1
        }
        let dataSet = LineChartDataSet(values: (chartEntries), label: "Price")
        dataSet.setColor(UIColor.blue)
        dataSet.setCircleColor(UIColor.white)
        dataSet.lineWidth = 5
        dataSet.circleRadius = 2
        dataSet.drawCircleHoleEnabled = false
        
        let data = LineChartData()
        data.addDataSet(dataSet)
        
        return data
    }
    
    public static func dateKey(date: Date) -> String {
        return date.description.substring(to: String.Index(10))
    }
    
}
