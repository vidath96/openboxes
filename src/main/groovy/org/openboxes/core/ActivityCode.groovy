/**
 * Copyright (c) 2012 Partners In Health.  All rights reserved.
 * The use and distribution terms for this software are covered by the
 * Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 * which can be found in the file epl-v10.html at the root of this distribution.
 * By using this software in any fashion, you are agreeing to be bound by
 * the terms of this license.
 * You must not remove this notice, or any other, from this software.
 **/
package org.pih.warehouse.core

enum ActivityCode {

    MANAGE_INVENTORY('MANAGE_INVENTORY'),
    ADJUST_INVENTORY('ADJUST_INVENTORY'),
    PLACE_ORDER('PLACE_ORDER'),
    PLACE_REQUEST('PLACE_REQUEST'),
    FULFILL_ORDER('FULFILL_ORDER'),
    FULFILL_REQUEST('FULFILL_REQUEST'),
    SEND_STOCK('SEND_STOCK'),
    RECEIVE_STOCK('RECEIVE_STOCK'),
    EXTERNAL('EXTERNAL'),

    // Activities for INTERNAL_LOCATION
    CROSS_DOCKING('CROSS_DOCKING'),
    PUTAWAY_STOCK('PUTAWAY_STOCK'),
    PICK_STOCK('PICK_STOCK'),

    // Requisition reason codes
    SUBSTITUTE_REQUISITION_ITEM('SUBSTITUTE_REQUISITION_ITEM'),
    MODIFY_REQUISITION_ITEM('MODIFY_REQUISITION_ITEM'),
    MODIFY_PICKLIST_ITEM('MODIFY_PICKLIST_ITEM')


    final String id

    ActivityCode(String id) { this.id = id }

    static list() {
        [
                MANAGE_INVENTORY,
                ADJUST_INVENTORY,
                PLACE_ORDER,
                PLACE_REQUEST,
                FULFILL_ORDER,
                FULFILL_REQUEST,
                SEND_STOCK,
                RECEIVE_STOCK,
                CROSS_DOCKING,
                PUTAWAY_STOCK,
                PICK_STOCK,
                EXTERNAL
        ]
    }
}
