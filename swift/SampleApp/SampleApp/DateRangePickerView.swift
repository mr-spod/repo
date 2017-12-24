//
//  DateRangePickerView.swift
//  SampleApp
//
//  Created by Sean O'Donnell on 12/24/17.
//  Copyright © 2017 Sean O'Donnell. All rights reserved.
//

import Foundation
import UIKit

class DateRangePickerView: UIView, UITextFieldDelegate {
    
    let startField = UITextField()
    let endField = UITextField()
    let startLabel = UILabel()
    let endLabel = UILabel()
    let submitButton = UIButton()
    let datePicker = UIDatePicker()
    var viewModel: ViewModel
    
    init(vm: ViewModel) {
        self.viewModel = vm
        super.init(frame: CGRect.zero)
        
        datePicker.translatesAutoresizingMaskIntoConstraints = false
        datePicker.addTarget(self, action: #selector(handleDatePicker(sender:)), for: UIControlEvents.valueChanged)
        datePicker.datePickerMode = .date
        
        let toolBar = UIToolbar()
        toolBar.barStyle = UIBarStyle.default
        toolBar.isTranslucent = true
        let done = UIBarButtonItem.init(title: "Done", style: .done, target: self, action: #selector(toolBarDonePressed))
        toolBar.setItems([done], animated: false)
        toolBar.sizeToFit()
        toolBar.isUserInteractionEnabled = true
        
        startField.translatesAutoresizingMaskIntoConstraints = false
        startField.font = UIFont.systemFont(ofSize: 18)
        startField.borderStyle = .roundedRect
        startField.textAlignment = .left
        startField.delegate = self
        startField.inputView = datePicker
        startField.inputAccessoryView = toolBar
        addSubview(startField)
        
        endField.translatesAutoresizingMaskIntoConstraints = false
        endField.font = UIFont.systemFont(ofSize: 18)
        endField.borderStyle = .roundedRect
        endField.textAlignment = .left
        endField.delegate = self
        endField.inputView = datePicker
        endField.inputAccessoryView = toolBar
        addSubview(endField)
        
        startLabel.translatesAutoresizingMaskIntoConstraints = false
        startLabel.text = "Start date:"
        startLabel.font = UIFont.systemFont(ofSize: 14)
        addSubview(startLabel)
        
        endLabel.translatesAutoresizingMaskIntoConstraints = false
        endLabel.text = "End date:"
        endLabel.font = UIFont.systemFont(ofSize: 14)
        addSubview(endLabel)
        
        submitButton.translatesAutoresizingMaskIntoConstraints = false
        submitButton.setTitle("Submit", for: .normal)
        submitButton.backgroundColor = UIColor.blue
        submitButton.layer.cornerRadius = 5.0
        addSubview(submitButton)
        
        let views: [String: Any] = ["start": startField,
                                    "end": endField,
                                    "startLabel": startLabel,
                                    "endLabel": endLabel,
                                    "button": submitButton]
        addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-[start]-15-[end]-30-[button]-|", metrics: [:], views: views))
        addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|-[startLabel]-15-[endLabel]-30-[button]-|", metrics: [:], views: views))
        addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-[startLabel]-20-[start(120)]|", metrics: [:], views: views))
        addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-[endLabel]-20-[end(120)]|", metrics: [:], views: views))
        addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "H:|-[button]-|", metrics: [:], views: views))
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        self.viewModel = ViewModel()
        super.init(coder: aDecoder)
    }
    
    func handleDatePicker(sender: UIDatePicker) {
        let dateString = datePicker.date.description.substring(to: String.Index(10))
        if (startField.isFirstResponder) {
            startField.text = dateString
        } else {
            endField.text = dateString
        }
    }
    
    func toolBarDonePressed() {
        startField.resignFirstResponder()
        endField.resignFirstResponder()
    }
    
}
