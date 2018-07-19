import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchaseInvoice } from 'app/shared/model/purchase-invoice.model';

@Component({
    selector: 'jhi-purchase-invoice-detail',
    templateUrl: './purchase-invoice-detail.component.html'
})
export class PurchaseInvoiceDetailComponent implements OnInit {
    purchaseInvoice: IPurchaseInvoice;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseInvoice }) => {
            this.purchaseInvoice = purchaseInvoice;
        });
    }

    previousState() {
        window.history.back();
    }
}
