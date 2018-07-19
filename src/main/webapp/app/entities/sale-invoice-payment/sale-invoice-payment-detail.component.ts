import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISaleInvoicePayment } from 'app/shared/model/sale-invoice-payment.model';

@Component({
    selector: 'jhi-sale-invoice-payment-detail',
    templateUrl: './sale-invoice-payment-detail.component.html'
})
export class SaleInvoicePaymentDetailComponent implements OnInit {
    saleInvoicePayment: ISaleInvoicePayment;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ saleInvoicePayment }) => {
            this.saleInvoicePayment = saleInvoicePayment;
        });
    }

    previousState() {
        window.history.back();
    }
}
