package org.pih.warehouse.picklist

import grails.testing.web.controllers.ControllerUnitTest
import org.pih.warehouse.inventory.Inventory
import org.pih.warehouse.inventory.InventoryItem
import org.pih.warehouse.requisition.*
// import org.springframework.mock.web.MockHttpServletResponse
// import Location
// import Person
// import Product
import grails.converters.JSON
import org.pih.warehouse.picklist.Picklist
import org.pih.warehouse.picklist.PicklistItem
import org.pih.warehouse.picklist.PicklistService
import org.pih.warehouse.requisition.RequisitionItem

// import ActivityCode
// import testutils.MockBindDataMixin

class PicklistControllerTests implements ControllerUnitTest{

  void testSave() {
        def picklist = new Picklist(id: "2345", version: 3)
        def requisitionItem = new RequisitionItem(id:"ri1")
        def inventoryItem = new InventoryItem(id: "ii1")
        def picklistItem = new PicklistItem(id:"3322", version: 3, requisitionItem: requisitionItem, inventoryItem: inventoryItem)
        mockDomain(Picklist, [picklist])
        mockDomain(PicklistItem, [picklistItem])
        picklist.addToPicklistItems(picklistItem)
        mockDomain(InventoryItem, [inventoryItem])
        mockDomain(RequisitionItem, [requisitionItem])

        def picklistServiceMock = mockFor(PicklistService)
        picklistServiceMock.demand.save{ data ->
            picklist
        }
        controller.picklistService = picklistServiceMock.createMock()

        controller.request.contentType = 'text/json' 
        controller.request.content ='{"id":"2345"}'

        controller.save()
        def response = controller.response.contentAsString
        assert response && response.size() > 0
        def jsonResponse = JSON.parse(response)

        assert jsonResponse.success
        assert jsonResponse.data.id == picklist.id
        assert jsonResponse.data.version == picklist.version
        assert jsonResponse.data.picklistItems.size() == 1
        assert jsonResponse.data.picklistItems[0].id == picklistItem.id
        assert jsonResponse.data.picklistItems[0].requisitionItemId == requisitionItem.id
        assert jsonResponse.data.picklistItems[0].inventoryItemId == inventoryItem.id
        assert jsonResponse.data.picklistItems[0].version == picklistItem.version

        picklistServiceMock.verify()
    }

     void testSaveWithErrors() {
        def picklist = new Picklist(id: "2345")
        def picklistItem = new PicklistItem(id:"3322")
        mockDomain(Picklist, [picklist])
        mockDomain(PicklistItem, [picklistItem])
        mockForConstraintsTests(Picklist)
        picklist.addToPicklistItems(picklistItem)

        def picklistServiceMock = mockFor(PicklistService)
        picklistServiceMock.demand.save { data ->
            picklist.validate()
            picklist
        }
        controller.picklistService = picklistServiceMock.createMock()

        controller.request.contentType = 'text/json' 
        controller.request.content ='{"id":"2345"}'

        controller.save()
        def response = controller.response.contentAsString
        assert response && response.size() > 0
        def jsonResponse = JSON.parse(response)

        assert !jsonResponse.success
        assert jsonResponse.errors
        picklistServiceMock.verify()
    }
}

