/**
 * Copyright (c) 2012 Partners In Health.  All rights reserved.
 * The use and distribution terms for this software are covered by the
 * Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 * which can be found in the file epl-v10.html at the root of this distribution.
 * By using this software in any fashion, you are agreeing to be bound by
 * the terms of this license.
 * You must not remove this notice, or any other, from this software.
 **/
package org.pih.warehouse.importer

import grails.util.Holders
import org.grails.plugins.excelimport.AbstractExcelImporter
import org.grails.plugins.excelimport.ExpectedPropertyType
import org.pih.warehouse.product.Product
import org.pih.warehouse.product.ProductCatalog
import org.pih.warehouse.product.ProductCatalogItem
import org.springframework.validation.BeanPropertyBindingResult

class ProductCatalogItemExcelImporter extends AbstractExcelImporter {

    def excelImportService

    static Map columnMap = [
            sheet    : 'Sheet1',
            startRow : 1,
            columnMap: [
                    'A': 'productCatalogCode',
                    'B': 'productCode',
                    'C': 'productName'
            ]
    ]

    static Map propertyMap = [
            productCatalogCode: ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            productCode       : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null]),
            productName       : ([expectedType: ExpectedPropertyType.StringType, defaultValue: null])
    ]


    ProductCatalogItemExcelImporter(String fileName) {
        super(fileName)
        excelImportService = Holders.grailsApplication.mainContext.getBean("excelImportService")
    }


    List<Map> getData() {
        return excelImportService.convertColumnMapConfigManyRows(workbook, columnMap, null, null, propertyMap)
    }

    void validateData(ImportDataCommand command) {
        command.data.eachWithIndex { params, index ->
            ProductCatalogItem productCatalogItem = createOrUpdateProductCatalogItem(params)
            if (!productCatalogItem.validate()) {
                productCatalogItem.errors.each { BeanPropertyBindingResult error ->
                    command.errors.reject("Row ${index + 1}: Product catalog item is invalid: ${error.getFieldError()}")
                }
            }
        }

    }

    void importData(ImportDataCommand command) {
        command.data.eachWithIndex { params, index ->
            ProductCatalogItem productCatalog = createOrUpdateProductCatalogItem(params)
            if (productCatalog.validate()) {
                productCatalog.save(failOnError: true)
            }
        }
    }


    ProductCatalogItem createOrUpdateProductCatalogItem(Map params) {
        Product product
        ProductCatalog productCatalog

        if (params.productCode) {
            product = Product.findByProductCode(params.productCode)
        }

        if (params.productCatalogCode) {
            productCatalog = ProductCatalog.findByCode(params.productCatalogCode)
        }

        ProductCatalogItem productCatalogItem = ProductCatalogItem.findByProductAndProductCatalog(product, productCatalog)
        if (!productCatalogItem) {
            productCatalogItem = new ProductCatalogItem()
            productCatalogItem.product = product
            productCatalogItem.productCatalog = productCatalog
        }
        return productCatalogItem
    }


}
