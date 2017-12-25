//
//  LineChartViewExt.swift
//  SampleApp
//
//  Created by Sean O'Donnell on 12/25/17.
//  Copyright © 2017 Sean O'Donnell. All rights reserved.
//

import Foundation
import Charts

extension LineChartView {
    
    private class LineChartXAxisFormatter: NSObject, IAxisValueFormatter {
        
        var labels: [String] = []
        
        func stringForValue(_ value: Double, axis: AxisBase?) -> String {
            return labels[Int(value)]
        }
        
        init(labels: [String]) {
            super.init()
            self.labels = labels
        }
    }
    
    private class LineChartYAxisFormatter: NSObject, IAxisValueFormatter {
        
        func stringForValue(_ value: Double, axis: AxisBase?) -> String {
            return formatter.string(from: NSNumber(value: value))!
        }
        
        let formatter = NumberFormatter()
        
        override init() {
            formatter.numberStyle = .currency
            formatter.locale = Locale.current
            super.init()
        }
    }
    
    func setLineChartData(lineData: LineChartData, xAxisLabels: [String], label: String) {
        
        let chartFormatter = LineChartXAxisFormatter(labels: xAxisLabels)
        let xAxis = XAxis()
        xAxis.valueFormatter = chartFormatter
        self.xAxis.valueFormatter = xAxis.valueFormatter
        let leftAxis = YAxis()
        leftAxis.valueFormatter = LineChartYAxisFormatter()
        self.leftAxis.valueFormatter = leftAxis.valueFormatter
        self.data = lineData
    }
}
