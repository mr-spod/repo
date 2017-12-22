//
//  ViewModel.swift
//  SampleApp
//
//  Created by Sean O'Donnell on 12/22/17.
//  Copyright © 2017 Sean O'Donnell. All rights reserved.
//

import Foundation

class ViewModel: NSObject {
    
    override init() {
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
    
    
    
}
