import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISaleInvoiceItem } from 'app/shared/model/sale-invoice-item.model';

@Component({
    selector: 'jhi-sale-invoice-item-detail',
    templateUrl: './sale-invoice-item-detail.component.html'
})
export class SaleInvoiceItemDetailComponent implements OnInit {
    saleInvoiceItem: ISaleInvoiceItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ saleInvoiceItem }) => {
            this.saleInvoiceItem = saleInvoiceItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
