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
    
    private class LineChartFormatter: NSObject, IAxisValueFormatter {
        
        var labels: [String] = []
        
        func stringForValue(_ value: Double, axis: AxisBase?) -> String {
            return labels[Int(value)]
        }
        
        init(labels: [String]) {
            super.init()
            self.labels = labels
        }
    }
    
    func setLineChartData(lineData: LineChartData, xAxisLabels: [String], label: String) {
        
        let chartFormatter = LineChartFormatter(labels: xAxisLabels)
        let xAxis = XAxis()
        xAxis.valueFormatter = chartFormatter
        self.xAxis.valueFormatter = xAxis.valueFormatter
        self.data = lineData
    }
}
