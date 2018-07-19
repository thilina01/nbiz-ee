import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { NBizEeBrandModule } from './brand/brand.module';
import { NBizEeCategoryModule } from './category/category.module';
import { NBizEeItemModule } from './item/item.module';
import { NBizEeSupplierModule } from './supplier/supplier.module';
import { NBizEePurchaseInvoiceModule } from './purchase-invoice/purchase-invoice.module';
import { NBizEePurchaseInvoiceItemModule } from './purchase-invoice-item/purchase-invoice-item.module';
import { NBizEeCustomerModule } from './customer/customer.module';
import { NBizEeSalesPersonModule } from './sales-person/sales-person.module';
import { NBizEeSaleInvoiceModule } from './sale-invoice/sale-invoice.module';
import { NBizEeSaleInvoiceItemModule } from './sale-invoice-item/sale-invoice-item.module';
import { NBizEeQuotationModule } from './quotation/quotation.module';
import { NBizEeQuotationItemModule } from './quotation-item/quotation-item.module';
import { NBizEePaymentMethodModule } from './payment-method/payment-method.module';
import { NBizEeSaleInvoicePaymentModule } from './sale-invoice-payment/sale-invoice-payment.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        NBizEeBrandModule,
        NBizEeCategoryModule,
        NBizEeItemModule,
        NBizEeSupplierModule,
        NBizEePurchaseInvoiceModule,
        NBizEePurchaseInvoiceItemModule,
        NBizEeCustomerModule,
        NBizEeSalesPersonModule,
        NBizEeSaleInvoiceModule,
        NBizEeSaleInvoiceItemModule,
        NBizEeQuotationModule,
        NBizEeQuotationItemModule,
        NBizEePaymentMethodModule,
        NBizEeSaleInvoicePaymentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NBizEeEntityModule {}
