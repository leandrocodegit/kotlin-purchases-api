package com.api.purchases.service.check

import com.api.purchases.enuns.CodeError
import com.api.purchases.exceptions.EntityException
import com.api.purchases.integracao.product.response.ProductResponse
import com.api.purchases.model.Purchases

class ValidProduct(var productResponse: ProductResponse, purchases: Purchases): ValidPurchases(purchases) {


   override fun check(){
        if(productResponse.quantidade < purchases.product.qtd)
            throw EntityException("Quantidade indisponivel em estoque", CodeError.STOCK_ERROR)
       if(purchases.product.qtd <= 0)
           throw EntityException("Quantidade indisponivel em estoque", CodeError.STOCK_ERROR)
    }
}