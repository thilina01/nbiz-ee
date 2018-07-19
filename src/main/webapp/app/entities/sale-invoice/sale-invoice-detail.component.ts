import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISaleInvoice } from 'app/shared/model/sale-invoice.model';

@Component({
    selector: 'jhi-sale-invoice-detail',
    templateUrl: './sale-invoice-detail.component.html'
})
export class SaleInvoiceDetailComponent implements OnInit {
    saleInvoice: ISaleInvoice;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ saleInvoice }) => {
            this.saleInvoice = saleInvoice;
        });
    }

    previousState() {
        window.history.back();
    }
}
