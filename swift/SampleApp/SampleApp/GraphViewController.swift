//
//  GraphViewController.swift
//  SampleApp
//
//  Created by Sean O'Donnell on 12/25/17.
//  Copyright © 2017 Sean O'Donnell. All rights reserved.
//

import Foundation
import UIKit
import Charts
import RxSwift

class GraphViewController: UIViewController {
    var viewModel: ViewModel
    let graphView = LineChartView()
    
    init(viewModel: ViewModel) {
        self.viewModel = viewModel
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder aDecoder: NSCoder) {
        self.viewModel = ViewModel()
        super.init(coder: aDecoder)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor.white
        
        graphView.translatesAutoresizingMaskIntoConstraints = false
        graphView.isHidden = false
        graphView.xAxis.labelRotationAngle = 45
        view.addSubview(graphView)
        
        let views: [String: Any] = ["graph": graphView,
                                    "top": topLayoutGuide,
                                    "bottom": bottomLayoutGuide]
        
        view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[top]-20-[graph]-[bottom]", metrics: [:], views: views))
        view.addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-[graph]-|", metrics: [:], views: views))
        
        let data = viewModel.graphData()
        let labels = viewModel.dateLabels()
        self.graphView.setLineChartData(lineData: data, xAxisLabels: labels, label: "BPI from X/X/X to X/X/X")
    }
    
    
}
