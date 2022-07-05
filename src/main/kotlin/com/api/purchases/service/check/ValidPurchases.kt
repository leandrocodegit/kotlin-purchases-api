package com.api.purchases.service.check

import com.api.purchases.model.Purchases

open class ValidPurchases(var purchases: Purchases)  {

   open fun check() {}
}