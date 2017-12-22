//
//  Api.swift
//  SampleApp
//
//  Created by Sean O'Donnell on 12/22/17.
//  Copyright © 2017 Sean O'Donnell. All rights reserved.
//

import Foundation
import RxSwift
import Alamofire

class Api: NSObject {
    public func bpiHistoricalData(fromDate: Date, toDate: Date) /*-> Observable<Dictionary<String, AnyObject>>*/ {
        let config = ViewModel.configDictionary()
        let url = URL(string: config["bpi_url"]!)
        let req = Alamofire.request(url!)
    }
}
