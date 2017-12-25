//
//  Api.swift
//  SampleApp
//
//  Created by Sean O'Donnell on 12/22/17.
//  Copyright © 2017 Sean O'Donnell. All rights reserved.
//

import Foundation
import RxSwift
import RxAlamofire
import Alamofire
import DateTools

class Api: NSObject {
    
    var alamofireManager: SessionManager
    
    override init() {
        let sessionConfiguration = URLSessionConfiguration.default
        sessionConfiguration.timeoutIntervalForRequest = 10
        self.alamofireManager = Alamofire.SessionManager(configuration: sessionConfiguration)
        super.init()
    }
    
    public func bpiHistoricalData(fromDate: Date, toDate: Date) -> Observable<DataRequest> {
        let config = ViewModel.configDictionary()
        
        let startDate = fromDate.description.substring(to: String.Index(10))
        let endDate = toDate.description.substring(to: String.Index(10))
        
        let parameterString = "?start=" + startDate + "&end=" + endDate
        let url = config["bpi_url"] as! String + parameterString
        
        return Observable<DataRequest>.just(Alamofire.request(url, method: HTTPMethod.get).validate())
    }
}
