import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchaseInvoiceItem } from 'app/shared/model/purchase-invoice-item.model';

@Component({
    selector: 'jhi-purchase-invoice-item-detail',
    templateUrl: './purchase-invoice-item-detail.component.html'
})
export class PurchaseInvoiceItemDetailComponent implements OnInit {
    purchaseInvoiceItem: IPurchaseInvoiceItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseInvoiceItem }) => {
            this.purchaseInvoiceItem = purchaseInvoiceItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
