package com.api.purchases.exceptions

import com.api.purchases.enuns.CodeError

class EntityException(message: String, var codeError: CodeError): CustomException(message)
