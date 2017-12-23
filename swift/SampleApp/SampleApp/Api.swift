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

class Api: NSObject {
    public static func bpiHistoricalData(fromDate: Date, toDate: Date) -> Observable<(HTTPURLResponse, Any)> {
        let config = ViewModel.configDictionary()
        return RxAlamofire.requestJSON(.get, config["bpi_url"]!)
    }
}
