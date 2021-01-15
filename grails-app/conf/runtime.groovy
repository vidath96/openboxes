import org.pih.warehouse.core.ActivityCode
import org.pih.warehouse.core.Constants
import org.pih.warehouse.core.IdentifierGeneratorTypeCode
import org.pih.warehouse.core.RoleType

// Organization identifier
openboxes.identifier.organization.format = Constants.DEFAULT_ORGANIZATION_NUMBER_FORMAT
openboxes.identifier.organization.minSize = 2
openboxes.identifier.organization.maxSize = 3

// Purchase Order identifier
openboxes.identifier.purchaseOrder.generatorType = IdentifierGeneratorTypeCode.SEQUENCE
openboxes.identifier.purchaseOrder.format = "PO-\${destinationPartyCode}-\${sequenceNumber}"
openboxes.identifier.purchaseOrder.properties = ["destinationPartyCode": "destinationParty.code"]

// Require approval on purchase orders
openboxes.purchasing.approval.enabled = false
openboxes.purchasing.approval.minimumAmount = 0.00
openboxes.purchasing.approval.defaultRoleTypes = [RoleType.ROLE_APPROVER]

// Experimental feature that approximates a costing method to provide a crude unit price used
// for inventory valuation.
//
// Possible values:
//  * UpdateUnitPriceMethodCode.USER_DEFINED_PRICE (default)
//  * UpdateUnitPriceMethodCode.AVERAGE_PURCHASE_PRICE
//  * UpdateUnitPriceMethodCode.FIRST_PURCHASE_PRICE
//  * UpdateUnitPriceMethodCode.LAST_PURCHASE_PRICE
openboxes.purchasing.updateUnitPrice.enabled = false
openboxes.purchasing.updateUnitPrice.method = UpdateUnitPriceMethodCode.USER_DEFINED_PRICE

openboxes {
    megamenu {
        dashboard {
            enabled = true
            label = "dashboard.label"
            defaultLabel = "Dashboard"
            href = "/${appName}/dashboard/index"
        }
        analytics {
            enabled = true
            requiredRole = RoleType.ROLE_ADMIN
            label = "analytics.label"
            defaultLabel = "Analytics"
            menuItems = [
                    // TODO: Add option to include label 'beta'
                    [label: "inventory.browse.label", defaultLabel: "Browse Inventory", href: "/${appName}/inventoryBrowser/index"],
                    [label: "inventory.snapshot.label", defaultLabel: "Inventory Snapshots", href: "/${appName}/snapshot/list"],
                    [label: "consumption.report.label", defaultLabel: "Consumption Report", href: "/${appName}/consumption/list"]
            ]
        }
        inventory {
            enabled = true
            label = "inventory.label"
            defaultLabel = "Inventory"
            requiredActivities = [ActivityCode.MANAGE_INVENTORY]
            subsections = [
                    [
                            label: "inventory.browse.label",
                            defaultLabel: "Browse Inventory",
                            menuItems: [
                                    [label: "inventory.browse.label", defaultLabel: "Browse Inventory", href: "/${appName}/inventory/browse?resetSearch=true"],
                                    // TODO: (Future improvement) Probably further options should be generated dynamicaly (with item count in bracket)...
                            ],
                    ],
                    [
                            label: "inventory.manage.label",
                            defaultLabel: "Manage Inventory",
                            menuItems: [
                                    [label: "inventory.manage.label", defaultLabel: "Manage Inventory", href: "/${appName}/inventory/manage"],
                                    [label: "inventory.import.label", defaultLabel: "Import Inventory", href: "/${appName}/batch/importData?type=inventory&execution=e1s1"]
                            ]
                    ]
            ]
        }
        purchasing {
            enabled = true
            label = "order.purchasing.label"
            defaultLabel = "Purchasing"
            subsections = [
                    [
                            label: "",
                            defaultLabel: "Purchasing",
                            menuItems: [
                                    [label: "order.createPurchase.label", defaultLabel: "Create Purchase Order", href: "/${appName}/purchaseOrder/index"],
                                    [label: "order.listPurchase.label", defaultLabel: "List Purchase Orders", href: "/${appName}/order/list?orderTypeCode=PURCHASE_ORDER"],
                                    [label: "shipment.shipfromPO.label", defaultLabel: "Ship from Purchase Order", href: "/${appName}/stockMovement/createCombinedShipments?direction=INBOUND"]
                            ]
                    ]
            ]
        }
        inbound {
            enabled = true
            label = "default.inbound.label"
            defaultLabel = "Inbound"
            subsections = [
                    [
                            label: "stockMovements.label",
                            defaultLabel: "Stock Movements",
                            menuItems: [
                                    [label: "inbound.create.label", defaultLabel: "Create Inbound Movement", href: "/${appName}/stockMovement/createInbound?direction=INBOUND"],
                                    [label: "stockRequest.create.label", defaultLabel: "Create Stock Request", href: "/${appName}/stockMovement/createRequest"],
                                    [label: "inbound.list.label", defaultLabel: "List Inbound Movements", href: "/${appName}/stockMovement/list?direction=INBOUND"]
                            ]
                    ],
                    [
                            label: "putAways.label",
                            defaultLabel: "Putaways",
                            menuItems: [
                                    [label: "react.putAway.createPutAway.label", defaultLabel: "Create Putaway", href: "/${appName}/putAway/index"],
                                    [label: "react.putAway.list.label", defaultLabel: "List Putaways", href: "/${appName}/order/list?orderTypeCode=TRANSFER_ORDER&status=PENDING"]
                            ]
                    ]
            ]
        }
        outbound {
            enabled = true
            label = "outbound.label"
            defaultLabel = "Outbound"
            subsections = [
                    [
                            label: "",
                            defaultLabel: "Stock Movement",
                            menuItems: [
                                    [label: "outbound.create.label", defaultLabel: "Create Outbound Movements", href: "/${appName}/stockMovement/createOutbound?direction=OUTBOUND"],
                                    [label: "outbound.list.label", defaultLabel: "List Outbound Movements", href: "/${appName}/stockMovement/list?direction=OUTBOUND"]
                            ]
                    ]
            ]
        }
        reporting {
            enabled = true
            label = "reporting.label"
            defaultLabel = "Reporting"
            subsections = [
                    [
                            label: "report.inventoryReports.label",
                            defaultLabel: "Inventory Reports",
                            menuItems: [
                                    [label: "report.inStockReport.label", defaultLabel: "In Stock Report", href: "/${appName}/inventory/listInStock"],
                                    [label: "report.binLocationReport.label", defaultLabel: "Bin Location Report", href: "/${appName}/report/showBinLocationReport"],
                                    [label: "report.expiredStockReport.label", defaultLabel: "Expired Stock Report", href: "/${appName}/inventory/listExpiredStock"],
                                    [label: "report.expiringStockReport.label", defaultLabel: "Expiring Stock Report", href: "/${appName}/inventory/listExpiringStock"],
                                    [label: "report.inventoryByLocationReport.label", defaultLabel: "Inventory By Location Report", href: "/${appName}/report/showInventoryByLocationReport"],
                                    [label: "report.cycleCount.label", defaultLabel: "Cycle Count Report", href: "/${appName}/cycleCount/exportAsCsv"],
                                    [label: "report.baselineQohReport.label", defaultLabel: "Baseline QoH Report", href: "/${appName}/inventory/show"],
                                    [label: "report.onOrderReport.label", defaultLabel: "Order Report", href: "/${appName}/report/showOnOrderReport"]
                            ]
                    ],
                    [
                            label: "report.transactionReports.label",
                            defaultLabel: "Transaction Reports",
                            menuItems: [
                                    [label: "report.showTransactionReport.label", defaultLabel: "Transaction Report", href: "/${appName}/report/showTransactionReport"],
                                    [label: "report.consumption.label", defaultLabel: "Consumption Report", href: "/${appName}/consumption/show"],
                                    [label: "report.requestDetailReport.label", defaultLabel: "Request Detail Report", href: "/${appName}/report/showRequestDetailReport"]
                            ]
                    ],
                    [
                            label: "dataExports.label",
                            defaultLabel: "Data Exports",
                            menuItems: [
                                    [label: "product.exportAsCsv.label", defaultLabel: "Export products", href: "/${appName}/product/exportAsCsv"],
                                    [label: "export.productSources.label", defaultLabel: "Export product sources", href: "/${appName}/productSupplier/export"],
                                    [label: "export.latestInventory.label", defaultLabel: "Export latest inventory date", href: "/${appName}/inventory/exportLatestInventoryDate"],
                                    [label: "export.inventoryLevels.label", defaultLabel: "Export inventory levels", href: "/${appName}/inventoryLevel/export"],
                                    [label: "export.requisitions.label", defaultLabel: "Export requisitions", href: "/${appName}/requisition/export"],
                                    [label: "export.binLocations.label", defaultLabel: "Export bin locations", href: "/${appName}/report/exportBinLocation?downloadFormat=csv"],
                                    [label: "export.productDemand.label", defaultLabel: "Export product demand", href: "/${appName}/report/exportDemandReport?downloadFormat=csv"]
                            ]
                    ]
            ]
        }
        products {
            enabled = true
            label = "products.label"
            defaultLabel = "Products"
            subsections = [
                    [
                            label: "", // No label
                            defaultLabel: "", // No label
                            menuItems: [
                                    [label: "attributes.label", defaultLabel: "Attributes", href: "/${appName}/attribute/list"],
                                    [label: "product.catalogs.label", defaultLabel: "Catalogs", href: "/${appName}/productCatalog/list"],
                                    [label: "categories.label", defaultLabel: "Categories", href: "/${appName}/category/tree"],
                                    [label: "product.components.label", defaultLabel: "Components", href: "/${appName}/productComponent/list"],
                                    [label: "productGroups.label", defaultLabel: "Generic Products", href: "/${appName}/productGroup/list"],
                                    [label: "inventoryLevels.label", defaultLabel: "Inventory Levels", href: "/${appName}/inventoryLevel/list"]
                            ]
                    ],
                    [
                            label: "", // No label
                            defaultLabel: "", // No label
                            menuItems: [
                                    [label: "products.label", defaultLabel: "Products", href: "/${appName}/product/list"],
                                    [label: "productSuppliers.label", defaultLabel: "Products Sources", href: "/${appName}/productSupplier/list"],
                                    [label: "product.associations.label", defaultLabel: "Products Associations", href: "/${appName}/productAssociation/list"],
                                    [label: "product.tags.label", defaultLabel: "Tags", href: "/${appName}/tag/list"],
                                    [label: "unitOfMeasure.label", defaultLabel: "Unit of Measure", href: "/${appName}/unitOfMeasure/list"],
                                    [label: "unitOfMeasureClass.label", defaultLabel: "Uom Class", href: "/${appName}/unitOfMeasureClass/list"],
                                    [label: "unitOfMeasureConversion.label", defaultLabel: "Uom Conversion", href: "/${appName}/unitOfMeasureConversion/list"]
                            ]
                    ],
                    [
                            label: "", // No label
                            defaultLabel: "", // No label
                            menuItems: [
                                    [label: "product.create.label", defaultLabel: "Create new product", href: "/${appName}/product/create"],
                                    [label: "product.batchEdit.label", defaultLabel: "Batch edit product", href: "/${appName}/product/batchEdit"],
                                    [label: "product.importAsCsv.label", defaultLabel: "Import products", href: "/${appName}/product/importAsCsv"],
                                    [label: "product.exportAsCsv.label", defaultLabel: "Export products", href: "/${appName}/product/exportAsCsv"],
                                    [label: "import.inventory.label", defaultLabel: "Import Inventory", href: "/${appName}/batch/importData?type=inventory"],
                                    [label: "import.inventoryLevel.label", defaultLabel: "Import Inventory Level", href: "/${appName}/batch/importData?type=inventoryLevel"]
                            ]
                    ]
            ]
        }
        requisitionTemplate {
            enabled = true
            label = "requisitionTemplates.label"
            defaultLabel = "Stock Lists"
            menuItems = [
                    [label: "requisitionTemplates.list.label", defaultLabel: "List stock lists", href: "/${appName}/requisitionTemplate/list"],
                    [label: "requisitionTemplates.create.label", defaultLabel: "Create stock list", href: "/${appName}/requisitionTemplate/create"],
            ]
        }
        configuration {
            enabled = true
            requiredRole = RoleType.ROLE_ADMIN
            label = "configuration.label"
            defaultLabel = "Configuration"
            subsections = [
                    [
                            label: "admin.label",
                            defaultLabel: "Administration",
                            menuItems: [
                                    [label: "default.settings.label", defaultLabel: "Settings", href: "/${appName}/admin/showSettings"],
                                    [label: "cache.label", defaultLabel: "Cache", href: "/${appName}/admin/cache"],
                                    [label: "console.label", defaultLabel: "Console", href: "/${appName}/console/index"],
                                    [label: "dataImport.label", defaultLabel: "Data Import", href: "/${appName}/batch/importData"],
                                    [label: "dataMigration.label", defaultLabel: "Data Migration", href: "/${appName}/migration/index"],
                                    [label: "email.label", defaultLabel: "Email", href: "/${appName}/admin/sendMail"],
                                    [label: "localization.label", defaultLabel: "Localization", href: "/${appName}/localization/list"]
                            ]
                    ],
                    [
                            label: "parties.label",
                            defaultLabel: "Locations",
                            menuItems: [
                                    [label: "locations.label", defaultLabel: "Locations", href: "/${appName}/location/list"],
                                    [label: "locationGroups.label", defaultLabel: "Location groups", href: "/${appName}/locationGroup/list"],
                                    [label: "locationTypes.label", defaultLabel: "Location types", href: "/${appName}/locationType/list"],
                                    [label: "organizations.label", defaultLabel: "Organizations", href: "/${appName}/organization/list"],
                                    [label: "partyRoles.label", defaultLabel: "Party roles", href: "/${appName}/partyRole/list"],
                                    [label: "partyTypes.label", defaultLabel: "Party types", href: "/${appName}/partyType/list"],
                                    [label: "person.list.label", defaultLabel: "People", href: "/${appName}/person/list"],
                                    [label: "roles.label", defaultLabel: "Roles", href: "/${appName}/role/list"],
                                    [label: "users.label", defaultLabel: "Users", href: "/${appName}/user/list"],
                            ]
                    ],
                    [
                            label: "transactions.label",
                            defaultLabel: "Transactions",
                            menuItems: [
                                    [label: "transactionsTypes.label", defaultLabel: "Transactions Types", href: "/${appName}/transactionType"],
                                    [label: "transactions.label", defaultLabel: "Transactions", href: "/${appName}/inventory/listAllTransactions"],
                                    [label: "transaction.add.label", defaultLabel: "Add transaction", href: "/${appName}/inventory/editTransaction"],
                                    [label: "import.inventory.label", defaultLabel: "Import Inventory", href: "/${appName}/batch/importData?type=inventory"],
                                    [label: "import.inventoryLevel.label", defaultLabel: "Import Inventory Level", href: "/${appName}/batch/importData?type=inventoryLevel"]
                            ]
                    ],
                    [
                            label: "default.other.label",
                            defaultLabel: "Other",
                            menuItems: [
                                    [label: "budgetCode.label", defaultLabel: "Budget Code", href: "/${appName}/budgetCode/list", requiredRole: RoleType.ROLE_ADMIN],
                                    [label: "containerTypes.label", defaultLabel: "Container Types", href: "/${appName}/containerType/list"],
                                    [label: "documents.label", defaultLabel: "Documents", href: "/${appName}/document/list"],
                                    [label: "documentTypes.label", defaultLabel: "Document Types", href: "/${appName}/documentType/list"],
                                    [label: "eventTypes.label", defaultLabel: "Event Types", href: "/${appName}/eventType/list"],
                                    [label: "glAccountType.label", defaultLabel: "GL Account Type", href: "/${appName}/glAccountType/list", requiredRole: RoleType.ROLE_ADMIN],
                                    [label: "glAccount.label", defaultLabel: "GL Account", href: "/${appName}/glAccount/list", requiredRole: RoleType.ROLE_ADMIN],
                                    [label: "orderAdjustmentType.label", defaultLabel: "Order Adjustment Type", href: "/${appName}/orderAdjustmentType/list", requiredRole: RoleType.ROLE_ADMIN],
                                    [label: "paymentMethodTypes.label", defaultLabel: "Payment Method Types", href: "/${appName}/paymentMethodType/list"],
                                    [label: "paymentTerms.label", defaultLabel: "Payment Terms", href: "/${appName}/paymentTerm/list"],
                                    [label: "shippers.label", defaultLabel: "Shippers", href: "/${appName}/shipper/list"],
                                    [label: "shipmentWorkflows.label", defaultLabel: "Shipment Workflows", href: "/${appName}/shipmentWorkflow/list"]
                            ]
                    ]
            ]
        }
        customLinks {
            enabled = true
            label = "customLinks.label"
            defaultLabel = "Custom Links"
            menuItems = [
                    //[label: "requestItemCreation.label", defaultLabel: "Request Item Creation", href: "", target: "_blank"],
            ]
        }

        orders {
            enabled = true
            label = "orders.label"
            defaultLabel = "Orders"
        }
        stockRequest {
            enabled = true
            label = "stockRequests.label"
            defaultLabel = "Stock Requests"
        }
        stockMovement {
            enabled = true
            label = "stockMovements.label"
            defaultLabel = "Stock Movements"
        }
        putaways {
            enabled = true
            label = "putaways.label"
            defaultLabel = "Putaways"
        }

        // deprecated megamenu configuration
        requisitions {
            enabled = false
            label = "requisitions.label"
            defaultLabel = "Requisitions"
        }
        shipping {
            enabled = false
            label = "shipping.label"
            defaultLabel = "Shipping"
        }
        receiving {
            enabled = false
            label = "receiving.label"
            defaultLabel = "Receiving"
        }
    }
}

openboxes {
    tablero {
        enabled = true
        configurations {
            personal {
                name = "My Dashboard"
                filters {}
            }
            warehouse {
                name = "Warehouse Management"
                filters {}
            }
            inventory {
                name = "Inventory Management"
                filters {}
            }
            transaction {
                name = "Transaction Management"
                filters {}
            }
            fillRate {
                name = "Fill Rate"
                filters {
                    category {
                        endpoint = "/${appName}/categoryApi/list"
                    }
                }
            }
        }
        endpoints {
            number {
                inProgressPutaways {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getInProgressPutaways"
                    archived = ['inventory', 'transaction', 'fillRate']
                    order = 4
                }
                inventoryByLotAndBin {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getInventoryByLotAndBin"
                    archived = ['inventory', 'transaction', 'fillRate']
                    order = 1
                }
                inProgressShipments {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getInProgressShipments"
                    archived = ['inventory', 'transaction', 'fillRate']
                    order = 3
                }
                receivingBin {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getReceivingBin"
                    archived = ['transaction', 'fillRate']
                    order = 2
                }
                itemsInventoried {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getItemsInventoried"
                    archived = ['personal', 'warehouse', 'transaction', 'fillRate']
                    order = 5
                }
                defaultBin {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getDefaultBin"
                    archived = ['personal', 'warehouse', 'transaction', 'fillRate']
                    order = 6
                }
                negativeInventory {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getProductWithNegativeInventory"
                    archived = ['personal', 'warehouse', 'transaction', 'fillRate']
                    order = 7
                }
                expiredStock {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getExpiredProductsInStock"
                    archived = ['personal', 'warehouse', 'transaction', 'fillRate']
                    order = 8
                }
                fillRateSnapshot {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getFillRateSnapshot"
                    archived = ['personal', 'warehouse', 'inventory']
                    order = 9
                }
                openStockRequests {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getOpenStockRequests"
                    archived = ['personal', 'warehouse', 'transaction', 'fillRate']
                    order = 10
                }
                inventoryValue {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getInventoryValue"
                    archived = ['personal', 'warehouse', 'inventory', 'transaction', 'fillRate']
                    order = 11
                }
            }
            graph {
                inventorySummary {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getInventorySummary"
                    archived = ['inventory', 'transaction', 'fillRate']
                    datalabel = true
                    order = 1
                    colors {
                        labels {
                            success = ["In stock"]
                            warning = ["Above maximum", "Below reorder", "Below minimum"]
                            error = ["No longer in stock"]
                        }
                    }
                }
                expirationSummary {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getExpirationSummary"
                    archived = ['inventory', 'transaction', 'fillRate']
                    timeFilter = true
                    order = 2
                    colors {
                        datasets {
                            state6 = ["Expiration(s)"]
                        }
                        labels {
                            state5 = [
                                    [code : "react.dashboard.timeline.today.label", message : "today"],
                                    [code : "react.dashboard.timeline.within30Days.label", message : "within 30 days"],
                                    [code : "react.dashboard.timeline.within90Days.label", message : "within 90 days"],
                                    [code : "react.dashboard.timeline.within180Days.label", message : "within 180 days"],
                                    [code : "react.dashboard.timeline.within360Days.label", message : "within 360 days"]
                            ]
                        }
                    }
                }
                incomingStock {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getIncomingStock"
                    archived = ['inventory', 'transaction', 'fillRate']
                    order = 3
                    colors {
                        datasets {
                            state6 = ["first"]
                            state7 = ["second"]
                            state8 = ["third"]
                        }
                    }
                }
                outgoingStock {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getOutgoingStock"
                    archived = ['inventory', 'transaction', 'fillRate']
                    order = 4
                    colors {
                        datasets {
                            success = ["first"]
                            warning = ["second"]
                            error = ["third"]
                        }
                    }
                }
                receivedStockMovements {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getReceivedStockMovements"
                    archived = ['personal', 'warehouse', 'inventory', 'fillRate']
                    timeFilter = true
                    stacked = true
                    datalabel = true
                    order = 7
                }
                discrepancy {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getDiscrepancy"
                    archived = ['inventory', 'transaction', 'fillRate']
                    timeFilter = true
                    order = 6
                }
                delayedShipments {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getDelayedShipments"
                    archived = ['transaction', 'fillRate']
                    order = 5
                    colors {
                        datasets {
                            state5 = ["first"]
                            state4 = ["second"]
                            state3 = ["third"]
                        }
                    }
                }
                sentStockMovements {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getSentStockMovements"
                    archived = ['personal', 'warehouse', 'inventory', 'fillRate']
                    timeFilter = true
                    stacked = true
                    datalabel = true
                    order = 8
                }
                lossCausedByExpiry {
                    enabled = false
                    endpoint = "/${appName}/apitablero/getLossCausedByExpiry"
                    archived = ['personal', 'warehouse', 'inventory', 'fillRate']
                    timeFilter = true
                    stacked = true
                    order = 9
                    colors {
                        datasets {
                            success = ["Inventory value not expired last day of month"]
                            warning = ["Inventory value expired last day of month"]
                            error = ["Inventory value removed due to expiry"]
                        }
                    }
                }
                productsInventoried {
                    enabled = false
                    endpoint = "/${appName}/apitablero/getProductsInventoried"
                    archived = ['personal', 'warehouse', 'transaction', 'fillRate']
                    order = 10
                    colors {
                        datasets {
                            state6 = ["first"]
                            state7 = ["second"]
                            state8 = ["third"]
                        }
                    }
                }
                percentageAdHoc {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getPercentageAdHoc"
                    archived = ['personal', 'warehouse', 'inventory', 'fillRate']
                    legend = true
                    datalabel = true
                    order = 11
                    colors {
                        labels {
                            state5 = ["STOCK"]
                            state4 = ["ADHOC"]
                        }
                    }
                }
                fillRate {
                    enabled = true
                    legend = true
                    endpoint = "/${appName}/apitablero/getFillRate"
                    archived = ['personal', 'warehouse', 'inventory']
                    timeFilter = true
                    locationFilter = true
                    timeLimit = 12
                    doubleAxeY = true
                    datalabel = false
                    size = 'big'
                    colors {
                        datasets {
                            state3 = ["Request lines submitted"]
                            state6 = ["Lines cancelled stock out"]
                            state2 = ["Average Fill Rate"]
                            state8 = ["Average of target Fill Rate"]
                        }
                    }
                    order = 12
                }
                stockOutLastMonth {
                    enabled = true
                    endpoint = "/${appName}/apitablero/getStockOutLastMonth"
                    archived = ['personal', 'warehouse', 'inventory', 'fillRate']
                    legend = true
                    datalabel = true
                    order = 13
                    colors {
                        labels {
                            success = ["Never"]
                            warning = ["Stocked out <1 week"]
                            state2  = ["Stocked out 1-2 weeks"]
                            state1  = ["Stocked out 2-3 weeks"]
                            error   = ["Stocked out 3-4 weeks"]
                        }
                    }
                }
            }
        }
    }
}

//Breadcrumbs configuration
breadcrumbsConfig {
    inbound {
        actionLabel = "react.stockMovement.inbound.create.label"
        defaultActionLabel = "Create Inbound"
        listLabel = "react.stockMovement.label"
        defaultListLabel = "Stock Movement"
        actionUrl = "/${appName}/stockMovement/createInbound/"
        listUrl   = "/${appName}/stockMovement/list?direction=INBOUND"
    }
    outbound {
        actionLabel = "react.stockMovement.outbound.create.label"
        defaultActionLabel = "Create Outbound"
        listLabel = "react.stockMovement.label"
        defaultListLabel = "Stock Movement"
        actionUrl = "/${appName}/stockMovement/createOutbound/"
        listUrl = "/${appName}/stockMovement/list?direction=OUTBOUND"
    }
    request {
        actionLabel = "react.stockMovement.request.create.label"
        defaultActionLabel = "Create Request"
        listLabel = "react.stockMovement.label"
        defaultListLabel = "Stock Movement"
        actionUrl = "/${appName}/stockMovement/createRequest/"
        listUrl = "/${appName}/stockMovement/list?direction=INBOUND"
    }
    verifyRequest {
        actionLabel = "react.stockMovement.request.verify.label"
        defaultActionLabel = "Verify Request"
        listLabel = "react.stockMovement.label"
        defaultListLabel = "Stock Movement"
        actionUrl = "/${appName}/stockMovement/list"
        listUrl = "/${appName}/stockMovement/list"
    }
    putAway {
        actionLabel = "react.putAway.createPutAway.label"
        defaultActionLabel = "Create Putaway"
        listLabel = "react.breadcrumbs.order.label"
        defaultListLabel = "Order"
        actionUrl = "/${appName}/putAway/create/"
        listUrl = "/${appName}/order/list?orderTypeCode=TRANSFER_ORDER&status=PENDING"
    }
    combinedShipments {
        actionLabel = "shipmentFromPO.label"
        defaultActionLabel = "Ship from PO"
        listLabel = "react.stockMovement.label"
        defaultListLabel = "Stock Movement"
        actionUrl = "/${appName}/stockMovement/createCombinedShipments/"
        listUrl   = "/${appName}/stockMovement/list?direction=INBOUND"
    }
}
