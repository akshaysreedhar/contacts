package com.akzhey.contacts.data.mappers

import com.akzhey.contacts.data.local.db.NumberItem
import com.akzhey.contacts.domain.models.PhoneNumber

fun PhoneNumber.toNumberItem(): NumberItem {
    return NumberItem(
        number = number,
        numberType = type
    )
}

fun List<NumberItem>.toPhoneNumberList(): List<PhoneNumber> {
    return this.map { numberItem ->
        PhoneNumber(number = numberItem.number, type = numberItem.numberType)
    }
}