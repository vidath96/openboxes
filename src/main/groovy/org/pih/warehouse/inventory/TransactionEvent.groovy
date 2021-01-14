/**
 * Copyright (c) 2012 Partners In Health.  All rights reserved.
 * The use and distribution terms for this software are covered by the
 * Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 * which can be found in the file epl-v10.html at the root of this distribution.
 * By using this software in any fashion, you are agreeing to be bound by
 * the terms of this license.
 * You must not remove this notice, or any other, from this software.
 **/
package org.pih.warehouse.inventory

import org.springframework.context.ApplicationEvent

class TransactionEvent extends ApplicationEvent {

    Boolean forceRefresh = false
    List associatedProducts
    String associatedLocation

    TransactionEvent(Transaction source) {
        super(source)
    }

    TransactionEvent(Transaction source, Boolean forceRefresh, List associatedProducts, String associatedLocation) {
        super(source)
        this.forceRefresh = forceRefresh
        this.associatedProducts = associatedProducts
        this.associatedLocation = associatedLocation
    }
}
